package edu.soft1.controller;

import com.sun.applet2.AppletParameters;
import edu.soft1.pojo.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    AppletParameters map = null;
    @RequestMapping(value = "upload",method = {RequestMethod.POST})
    public  String fileUpload(MultipartFile image, HttpServletRequest request) throws IOException {
        InputStream is = image.getInputStream();//输入流
        String filename = image.getOriginalFilename();//文件名
        String realPath = request.getServletContext().getRealPath("/images");
        System.out.println("上传路径="+realPath);
        FileOutputStream os = new FileOutputStream(new File(realPath,doFileName(filename)));
        int size = IOUtils.copy(is,os);
        if (size > 0){
            map.put("msg",size);
        }
        System.out.println("完成上传size="+size+"Byte");
        is.close();os.close();//关闭流
        return "welcome";
    }

    @RequestMapping(value = "upload2",method = {RequestMethod.POST})
    public  String fileUpload2(MultipartFile[] images,HttpServletRequest request) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        int count=0;
        for (MultipartFile image:images) {
            is = image.getInputStream();//输入流
            String filename = image.getOriginalFilename();//文件名称
            System.out.println("文件原名称="+filename);
            if (filename.equals("")){
                System.out.println("空字符串，进入下一轮循环");
                continue;//进入下一轮循环
            }
            String realPath = request.getServletContext().getRealPath("/images");
            System.out.println("上传路径=" + realPath);
            os = new FileOutputStream(new File(realPath, doFileName(filename)));
            int size = IOUtils.copy(is, os);
            if (size > 0){
                count++;}
            /*  System.out.println("完成上传size=" + size + "Byte");
            IOUtils.copy(is,os); //把输入流文件写入到输出流,完成文件的上传*/
        }
        os.close();
        is.close();//关闭流
        map.put("msg2",count);
        System.out.println("上传成功"+count+"张图片");
        return "welcome";
    }

    private  String doFileName(String filename){
        String extension = FilenameUtils.getExtension(filename);//获取文件的后缀名称
        String uuid = UUID.randomUUID().toString();//读取uuid字符，规避名称重复
        System.out.println("上传文件名="+uuid);
        return uuid+"."+extension;
    }

    @RequestMapping("/hello")
    public String hello(/*String username, Model model*/){
        System.out.println("----hello()----");
       /* //将传入的参数添加到Model对象(存入request作用域)
        model.addAttribute("username",username);*/
        return "hello";
    }

    @RequestMapping(value = "/login")
    public String login(User user, HttpSession session, HttpServletRequest request){
        System.out.println("---login()----");
        //调用业务,获取flag的值
        request.getParameter("");
        System.out.println("username="+user.getUsername());
        int flag=1;
        if(flag==1){
            System.out.println("username="+user.getUsername());
            session.setAttribute("user",user);//登陆对象放入session
            return "welcome";//成功
        }
        System.out.println("登陆失败，请重新尝试");
        request.setAttribute("error","用户名或密码不正确");
    return "forward:/index.jsp";//失败
    }

    @RequestMapping("reg")
    public String reg(User user){
        System.out.println("username="+user.getUsername());
        System.out.println("pwd="+user.getPwd());
        System.out.println("age="+user.getAge());
        System.out.println("birthday="+user.getBirthday());
        System.out.println("city="+user.getAddress().getCity());
        System.out.println("street="+user.getAddress().getStreet());
        System.out.println("phone="+user.getAddress().getPhone());
        return "hello";
    }
    @RequestMapping("/delete")
    public String delete(){
        System.out.println("-----delete()-----");
        return "welcome";
    }
    @RequestMapping("/logout")//登出功能
    public String logout(HttpSession session){
        //清空session
        session.invalidate();
        System.out.println("已退出");
        return "redirect:/index.jsp";//重定向跳转到首页
    }
   /* @RequestMapping(value = "/load.do/{filename}")
    public void load(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition","attachment;filename="+文件名);
        String realPath= request.getServletContext().getRealPath("/images");
        System.out.println("下载路径="+realPath);
        FileInputStream is = new FileInputStream(new File(realPath,filename));
        OutputStream os = response.getOutputStream();
        IOUtils.copy(is,os);
        os.close();is.close();
    }*/

}
