package com.yzm.majoryc.controller.admin;

import com.yzm.majoryc.DO.admin.UserDO;
import com.yzm.majoryc.VO.RestResult;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.admin.AddUserRoleVO;
import com.yzm.majoryc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public RestResult getUserList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                  @RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "phone", required = false) String phone) {
        TableInfo<List<UserDO>> userList = userService.getUserList(page, limit, username, phone);
        return RestResult.success(userList);
    }

    @PostMapping("/addUser")
    public RestResult addUser(@RequestBody AddUserRoleVO addUserRoleVO) {
        userService.addUser(addUserRoleVO);
        return RestResult.success();
    }

    @DeleteMapping("/{id}")
    public RestResult deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
        return RestResult.success();
    }
}
