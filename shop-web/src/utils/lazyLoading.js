export default (name, index = false) => () => import(`@/views/${name}.vue`)
