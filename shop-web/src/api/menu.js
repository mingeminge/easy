import request from '@/utils/request'

export function GetMenu(id) {
  return request({
    url: '/router/getRouter',
    method: 'get',
    params: id
  })
}
