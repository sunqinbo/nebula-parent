package com.olymtech.nebula.web.filter;

import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.common.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private IUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        NebulaUserInfo user = userService.findByUsername(username);
        request.setAttribute(Constants.CURRENT_USER, user);
        Set<String> permissions = userService.findPermissionsByEmpId(user.getEmpId());
        Set<String> roles = userService.findRolesByEmpId(user.getEmpId());
        request.setAttribute("role", roles);
        request.setAttribute("permission", permissions);
        return true;
    }

}
