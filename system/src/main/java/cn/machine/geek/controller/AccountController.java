package cn.machine.geek.controller;

import cn.machine.geek.common.P;
import cn.machine.geek.common.R;
import cn.machine.geek.entity.Account;
import cn.machine.geek.service.AccountService;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: MachineGeek
 * @Description: 账户控制器
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
    * @Author: MachineGeek
    * @Description: 根据ID获取
    * @Date: 2021/1/7
     * @param id
    * @Return: cn.machine.geek.common.R
    */
    @GetMapping("/getById")
    public R getById(@RequestParam("id") Long id){
        return R.ok(accountService.getById(id));
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
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        String keyword = p.getKeyword();
        if(!StringUtils.isEmpty(keyword)){
            queryWrapper.lambda().like(Account::getName,keyword);
        }
        return R.ok(accountService.page(new Page<>(p.getPage(),p.getSize())));
    }

    /**
    * @Author: MachineGeek
    * @Description: 添加
    * @Date: 2021/1/7
     * @param account
    * @Return: cn.machine.geek.common.R
    */
    @PostMapping("/add")
    public R add(@RequestBody Account account){
        return R.ok(accountService.save(account));
    }

    /**
    * @Author: MachineGeek
    * @Description: 修改
    * @Date: 2021/1/7
     * @param account
    * @Return: cn.machine.geek.common.R
    */
    @PutMapping("/modifyById")
    public R modifyById(@RequestBody Account account){
        return R.ok(accountService.updateById(account));
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
        return R.ok(accountService.removeById(id));
    }
}