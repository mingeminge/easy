import request from '@/utils/request';

export function addUser(params) {
  return request({
    url: '/user/addUser',
    method: 'post',
    data: params
  })
}

export function getUsers(params) {
  return request({
    url: '/user/list',
    method: 'get',
    params: params
  })
}

export function deleteUser(id) {
  return request({
    url: '/user/' + id,
    method: 'delete'
  })
}

export function getRoles(roleName) {
  return request({
    url: '/role/list',
    method: 'get',
    params: roleName
  })
}

export function addRole(roleName) {
  return request({
    url: '/role/add',
    method: 'post',
    data: roleName
  })
}

export function deleteRole(roleId) {
  return request({
    url: '/role/delete/' + roleId,
    method: 'delete'
  })
}

export function getPermissionByRoleId(roleId) {
  return request({
    url: 'role/getPermissions/' + roleId,
    method: 'get'
  })
}

export function getRolesByUserId(id) {
  return request({
    url: '/role/getRoleByUserId/' + id,
    method: 'get'
  })
}


