/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}
export function isLegalHttpUrl(rule, value, callback) {
  let reg = /^(http|https):\/\/([\w.]+\/?)\S*/
  if (!reg.test(value)) {
    callback(new Error("请输入正确网址以http或https开头"));
    return;
  }
  callback();
}
export function isJson(rule, value, callback) {
  try {
    JSON.parse(value);
  } catch (e) {
    callback(new Error("请输入json格式"))
    return;
  }
  callback()
}
