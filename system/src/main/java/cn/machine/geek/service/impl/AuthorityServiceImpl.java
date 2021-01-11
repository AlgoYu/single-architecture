package cn.machine.geek.service.impl;

import cn.machine.geek.dto.AuthorityTreeNode;
import cn.machine.geek.entity.Authority;
import cn.machine.geek.mapper.AuthorityMapper;
import cn.machine.geek.service.AuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MachineGeek
 * @Description: 权力服务实现类
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {
    @Override
    public List<Authority> listByRoleId(Long roleId) {
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    public List<Authority> listByAccountId(Long accountId) {
        return baseMapper.selectByAccountId(accountId);
    }

    @Override
    public List<AuthorityTreeNode> tree() {
        return getChild(0L,baseMapper.selectList(null));
    }

    /**
    * @Author: MachineGeek
    * @Description: 获取子节点
    * @Date: 2021/1/11
     * @param id
     * @param authorities
    * @Return: java.util.List<cn.machine.geek.dto.AuthorityTreeNode>
    */
    private List<AuthorityTreeNode> getChild(Long id,List<Authority> authorities){
        List<AuthorityTreeNode> child = new ArrayList<>();
        authorities.forEach((authority)->{
            if(authority.getParentId().equals(id)){
                AuthorityTreeNode authorityTreeNode = new AuthorityTreeNode();
                BeanUtils.copyProperties(authority, authorityTreeNode);
                authorityTreeNode.setChild(getChild(authorityTreeNode.getId(),authorities));
                child.add(authorityTreeNode);
            }
        });
        return child;
    }
}
