import request from '@/utils/request';

export function getOrderList(params) {
  return request({
    url: '/order/list',
    method: 'get',
    params: params
  })
}

export function update(param) {
  return request({
    url: '/order/update',
    method: 'post',
    data: param
  })
}

export function add(param) {
  return request({
    url: '/order/add',
    method: 'post',
    data: param
  })
}

export function deleteOrderByOrderId(id) {
  return request({
    url: '/order/' + id,
    method: 'delete'
  })
}

export function addDaily(param) {
  return request({
    url: '/order/addDaily',
    method: 'post',
    data: param
  })
}


export function dailyList(param) {
  return request({
    url: '/order/dailyList',
    method: 'get',
    params: param
  })
}

