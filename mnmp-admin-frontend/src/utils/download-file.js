// create by @lianglei
/**
 * 使用二进制流形式下载文件，必须设置 responseType: 'blob'
 * @param {Object} blobData
 * @param {String} fileName 格式 name.ext
 */
export function downloadFileByBlob (blobData, fileName, type) {
  if (!blobData || !fileName) {
    return false
  }
  console.log(blobData)
  const blob_ = new Blob([blobData], { type: type })
  if ('download' in document.createElement('a')) {
    const url_ = window.URL.createObjectURL(blob_)
    const aLink = document.createElement('a')
    aLink.style.display = 'none'
    aLink.href = url_
    aLink.setAttribute('download', fileName)
    document.body.appendChild(aLink)
    aLink.click()
    window.URL.revokeObjectURL(url_) // 释放掉blob对象
    document.body.removeChild(aLink) // 下载完成移除元素
  } else {
    navigator.msSaveBlob(blob_, fileName)
  }
}

/**
 * 将blob对象转化为json（文件类型调用ajax 取后端的返回值做特殊处理）
 * @param {Object} blob
 * @example
 * if (error.response.status === 500 && error.response.config.responseType === 'blob') {
 *    msg_ = (await blobToJson(error.response.data)).tip
 *  }
 */
export function blobToJson (blob) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = res => {
      const { result } = res.target // 得到字符串
      const data = JSON.parse(result) // 解析成json对象
      resolve(data)
    } // 成功回调
    reader.onerror = err => {
      reject(err)
    } // 失败回调
    reader.readAsText(new Blob([blob]), 'utf-8') // 按照utf-8编码解析
  })
}
