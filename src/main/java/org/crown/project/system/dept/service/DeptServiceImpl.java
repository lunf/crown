package org.crown.project.system.dept.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.annotation.DataScope;
import org.crown.common.cons.UserConstants;
import org.crown.common.utils.StringUtils;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.dept.domain.Dept;
import org.crown.project.system.dept.mapper.DeptMapper;
import org.crown.project.system.role.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Department management service realization
 *
 * @author Crown
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    @DataScope
    public List<Dept> selectDeptList(Dept dept) {
        return baseMapper.selectDeptList(dept);
    }

    @Override
    @DataScope
    public List<Ztree> selectDeptTree(Dept dept) {
        List<Dept> deptList = baseMapper.selectDeptList(dept);
        return initZtree(deptList);
    }

    @Override
    public List<Ztree> roleDeptTreeData(Role role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees;
        List<Dept> deptList = selectDeptList(new Dept());
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList = baseMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        } else {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * Object to department tree
     *
     * @param deptList Department list
     * @return Tree structure list
     */
    public List<Ztree> initZtree(List<Dept> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * Object to department tree
     *
     * @param deptList     Department list
     * @param roleDeptList Role already exists in the menu list
     * @return Tree structure list
     */
    public List<Ztree> initZtree(List<Dept> deptList, List<String> roleDeptList) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (Dept dept : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck) {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        return query().eq(Dept::getDeptId, deptId).exist();
    }

    @Override
    public boolean insertDept(Dept dept) {
        Dept info = baseMapper.selectDeptById(dept.getParentId());
        // If the parent node is not in the "normal" state, no new child nodes are allowed
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "Department is disabled, adding is not allowed");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return save(dept);
    }

    @Override
    @Transactional
    public boolean updateDept(Dept dept) {
        Dept newParentDept = baseMapper.selectDeptById(dept.getParentId());
        Dept oldDept = selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        boolean result = updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
            // If the department is enabled, then all the higher-level departments of the department are enabled
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * Modify the status of the parent department of the department
     *
     * @param dept Current department
     */
    private void updateParentDeptStatus(Dept dept) {
        String updateBy = dept.getUpdateBy();
        dept = baseMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        baseMapper.updateDeptStatus(dept);
    }

    /**
     * Modify the child element relationship
     *
     * @param deptId       Department ID being modified
     * @param newAncestors New parent ID collection
     * @param oldAncestors Old parent ID collection
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<Dept> children = baseMapper.selectChildrenDeptById(deptId);
        for (Dept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            baseMapper.updateDeptChildren(children);
        }
    }

    /**
     * Modify the child element relationship
     *
     * @param deptId    Department ID
     * @param ancestors Element list
     */
    public void updateDeptChildren(Long deptId, String ancestors) {
        Dept dept = new Dept();
        dept.setParentId(deptId);
        List<Dept> childrens = baseMapper.selectDeptList(dept);
        for (Dept children : childrens) {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0) {
            baseMapper.updateDeptChildren(childrens);
        }
    }

    @Override
    public Dept selectDeptById(Long deptId) {
        return baseMapper.selectDeptById(deptId);
    }

    @Override
    public boolean checkDeptNameUnique(Dept dept) {
        Long deptId = dept.getDeptId();
        Dept info = query().eq(Dept::getDeptName, dept.getDeptName()).eq(Dept::getParentId, dept.getParentId()).getOne();
        return Objects.isNull(info) || info.getDeptId().equals(deptId);
    }
}
