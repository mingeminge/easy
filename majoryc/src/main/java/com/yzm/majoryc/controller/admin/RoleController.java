package com.yzm.majoryc.controller.admin;

import com.yzm.majoryc.VO.RestResult;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.admin.RoleVO;
import com.yzm.majoryc.VO.admin.RolesHasRoles;
import com.yzm.majoryc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public RestResult getRoleList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        TableInfo<List<RoleVO>> roleList = roleService.getRoleList(page, limit);
        return RestResult.success(roleList);
    }

    @GetMapping("/getRoleByUserId/{id}")
    public RestResult getRoleByUserId(@PathVariable("id") Integer id) {
        RolesHasRoles roleByUserId = roleService.getRoleByUserId(id);
        return RestResult.success(roleByUserId);
    }
}
