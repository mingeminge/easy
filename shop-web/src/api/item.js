import request from '@/utils/request';


export function getItemList(param) {
  return request({
    url: '/item/itemList',
    method: 'get',
    params: param
  })
}
