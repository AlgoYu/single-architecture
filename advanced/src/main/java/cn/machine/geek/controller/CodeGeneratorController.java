package cn.machine.geek.controller;

import cn.machine.geek.common.P;
import cn.machine.geek.common.R;
import cn.machine.geek.service.DatabaseService;
import cn.machine.geek.util.CodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: MachineGeek
 * @Description: 代码生成器控制器
 * @Date: 2020/11/6
 */
@Api(tags = "代码生成器接口")
@RestController
@RequestMapping(value = "/generator")
public class CodeGeneratorController {
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private CodeGenerator codeGenerator;

    @ApiOperation(value = "获取数据库表",notes = "获取数据库表")
    @GetMapping(value = "/paging")
    public R paging(@Validated P p){
        return R.ok(databaseService.paging(p.getPage(),p.getSize(),p.getKeyword()));
    }

    @ApiOperation(value = "生成代码",notes = "生成代码")
    @GetMapping(value = "/generate")
    public void generate(@RequestParam(value = "tableName") String tableName, @RequestParam(value = "moduleName") String moduleName,@RequestParam(value = "packageName",required = false,defaultValue = "cn.machine.geek") String packageName, HttpServletResponse response){
        String filePath = codeGenerator.generate(tableName, packageName, moduleName);
        response.setContentType("application/octet-stream");
        File file = new File(filePath);
        response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
        try {
            IOUtils.copy(new FileInputStream(file),response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
