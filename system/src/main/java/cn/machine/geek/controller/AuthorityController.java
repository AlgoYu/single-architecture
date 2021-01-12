package cn.machine.geek.controller;

import cn.machine.geek.common.P;
import cn.machine.geek.common.R;
import cn.machine.geek.dto.AuthorityTree;
import cn.machine.geek.entity.Authority;
import cn.machine.geek.security.CustomUserDetail;
import cn.machine.geek.service.AuthorityService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MachineGeek
 * @Description: 权力控制器
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@Api(tags = "权力接口")
@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

    /**
    * @Author: MachineGeek
    * @Description: 根据ID获取
    * @Date: 2021/1/7
     * @param id
    * @Return: cn.machine.geek.common.R
    */
    @GetMapping("/getById")
    public R getById(@RequestParam("id") Long id){
        return R.ok(authorityService.getById(id));
    }

    /**
    * @Author: MachineGeek
    * @Description: 分页获取
    * @Date: 2021/1/7
     * @param p
    * @Return: cn.machine.geek.common.R
    */
    @GetMapping("/paging")
    public R paging(@Validated P p){
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        String keyword = p.getKeyword();
        if(!StringUtils.isEmpty(keyword)){
            queryWrapper.lambda().like(Authority::getName,keyword)
                    .like(Authority::getKey,keyword);
        }
        return R.ok(authorityService.page(new Page<>(p.getPage(),p.getSize())));
    }

    /**
    * @Author: MachineGeek
    * @Description: 获取权力树
    * @Date: 2021/1/11
     * @param
    * @Return: cn.machine.geek.common.R
    */
    @GetMapping("/tree")
    public R tree(){
        return R.ok(getChild(0l,authorityService.list()));
    }

    /**
    * @Author: MachineGeek
    * @Description: 获取当前用户的权限
    * @Date: 2021/1/11
     * @param
    * @Return: cn.machine.geek.common.R
    */
    @GetMapping("/treeByCurrent")
    public R treeByCurrent(){
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return R.ok(getChild(0l,authorityService.listByAccountId(customUserDetail.getId())));
    }

    /**
     * @Author: MachineGeek
     * @Description: 构建权限树子节点
     * @Date: 2021/1/11
     * @param id
     * @param authorities
     * @Return: java.util.List<cn.machine.geek.dto.AuthorityTreeNode>
     */
    private List<AuthorityTree> getChild(Long id, List<Authority> authorities){
        List<AuthorityTree> child = new ArrayList<>();
        authorities.forEach((authority)->{
            if(authority.getPid().equals(id) && authority.getUri() != null){
                AuthorityTree authorityTree = new AuthorityTree();
                BeanUtils.copyProperties(authority, authorityTree);
                authorityTree.setChild(getChild(authorityTree.getId(),authorities));
                authorityTree.setAuth(getAuth(authorityTree.getId(),authorities));
                child.add(authorityTree);
            }
        });
        return child;
    }

    /**
    * @Author: MachineGeek
    * @Description: 获取权限
    * @Date: 2021/1/12
     * @param id
    * @param authorities
    * @Return: java.util.List<java.lang.String>
    */
    private List<String> getAuth(Long id,List<Authority> authorities){
        List<String> auth = new ArrayList<>();
        authorities.forEach((authority)->{
            if(authority.getPid().equals(id) && authority.getUri() == null){
                auth.add(authority.getKey());
            }
        });
        if(auth.size() <= 0){
            return null;
        }
        return auth;
    }

    /**
    * @Author: MachineGeek
    * @Description: 添加
    * @Date: 2021/1/7
     * @param authority
    * @Return: cn.machine.geek.common.R
    */
    @PostMapping("/add")
    public R add(@RequestBody Authority authority){
        return R.ok(authorityService.save(authority));
    }

    /**
    * @Author: MachineGeek
    * @Description: 修改
    * @Date: 2021/1/7
     * @param authority
    * @Return: cn.machine.geek.common.R
    */
    @PutMapping("/modifyById")
    public R modifyById(@RequestBody Authority authority){
        return R.ok(authorityService.updateById(authority));
    }

    /**
    * @Author: MachineGeek
    * @Description: 通过ID删除
    * @Date: 2021/1/7
     * @param id
    * @Return: cn.machine.geek.common.R
    */
    @DeleteMapping("/modifyById")
    public R deleteById(@RequestParam("id") Long id){
        return R.ok(authorityService.removeById(id));
    }
}