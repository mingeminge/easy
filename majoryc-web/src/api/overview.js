import request from '@/utils/request';

export function shiftOutput() {
  return request({
    url: '/order/shiftOutput',
    method: 'get',
  })
}
export function outputDay() {
  return request({
    url: '/order/outputDay',
    method: 'get'
  })
}
