package com.baizhi.controller;

import com.baizhi.entity.Employee;
import com.baizhi.service.EmpService;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author MiracloW
 */
@Controller
@RequestMapping("employee")
public class EmpController {


    private static final Logger log = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpService empService;

    @Value("${photo.file.dir}")
    private String realpath;

    /**
     * 根据id查询员工详细信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("detail")
    public String detail(Integer id, Model model) {
        log.debug("当前查询员工id: {}", id);
        //1.根据id查询一个
        Employee employee = empService.findById(id);
        model.addAttribute("employee", employee);
        return "updateEmp";//跳转到更新页面
    }

    /**
     * 保存员工信息
     * 文件上传: 1.表单提交方式必须是post  2.表单enctype属性必须为 multipart/form-data
     * @return
     */
    @RequestMapping("save")
    public String save(Employee employee, MultipartFile img) throws IOException {
        log.debug("姓名:{}, 薪资:{}, 生日:{} ", employee.getName(), employee.getSalary(), employee.getBirthday());
        String originalFilename = img.getOriginalFilename();
        log.debug("头像名称: {}", originalFilename);
        log.debug("头像大小: {}", img.getSize());
        log.debug("上传的路径: {}", realpath);

        //1.处理头像的上传&修改文件名称
        String newFileName = uploadPhoto(img, originalFilename);
        //2.保存员工信息
        employee.setPhoto(newFileName);//保存头像名字
        empService.save(employee);
        return "redirect:/employee/lists";//保存成功跳转到列表页面
    }

    //上传头像方法(返回文件名)
    private String uploadPhoto(MultipartFile img, String originalFilename) throws IOException {
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix + fileNameSuffix;
        img.transferTo(new File(realpath, newFileName));
        return newFileName;
    }

    /**
     * 更新员工信息
     *
     * @param employee
     * @param img
     * @return
     */
    @RequestMapping("update")
    public String update(Employee employee, MultipartFile img) throws IOException {

        log.debug("更新之后员工信息: id:{},姓名:{},工资:{},生日:{},", employee.getId(), employee.getName(), employee.getSalary(), employee.getBirthday());
        //1.判断是否更新头像
        boolean notEmpty = !img.isEmpty();
        log.debug("是否更新头像: {}", notEmpty);
        if (notEmpty) {
            //1.删除老的头像 根据id查询原始头像
            String oldPhoto = empService.findById(employee.getId()).getPhoto();
            if(oldPhoto != null){
                File file = new File(realpath, oldPhoto);
                if (file.exists()) file.delete();//删除文件
            }
            //2.处理新的头像上传
            String originalFilename = img.getOriginalFilename();
            String newFileName = uploadPhoto(img, originalFilename);
            //3.修改员工新的头像名称
            employee.setPhoto(newFileName);
        }
        //2.没有更新头像直接更新基本信息
        empService.update(employee);
        return "redirect:/employee/lists";//更新成功,跳转到员工列表
    }

    /**
     * 删除员工信息
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String delete(Integer id){
        log.debug("删除的员工id: {}",id);
        //1.删除数据
        String photo = empService.findById(id).getPhoto();
        empService.delete(id);
        //2.删除头像
        if(photo != null) {
            File file = new File(realpath, photo);
            if (file.exists()) file.delete();
        }
        return "redirect:/employee/lists";//跳转到员工列表
    }

    /**
     * 员工列表
     *
     * @return
     */
    @RequestMapping("lists")
    public String lists(Model model) {
        log.debug("查询所有员工信息");
        List<Employee> employeeList = empService.lists();
        model.addAttribute("employeeList", employeeList);
        return "emplist";
    }

}
