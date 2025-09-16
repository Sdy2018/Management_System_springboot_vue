import axios from 'axios'

const request = axios.create({
        baseURL:import.meta.env.VITE_API_BASE_URL||"http://localhost:8080/",
        timeout:1000,
        headers: {
            'Content-Type': 'application/json',
            'X-App-Version': '1.0.0' // 自定义公共头
        }
}
)

request.interceptors.response.use(
    response => {
        // 统一处理数据格式（假设后端返回 { code, data, msg }）
        const { code, data, msg } = response.data

        if (code === 200) {
            return data
        } else {
            // 业务错误处理（如弹窗提示）
            // console.error(`[${msg}]`, response.config.url)
            return Promise.reject(new Error(msg || '请求失败'))
        }

    },
    error => {
        // 网络错误处理
        if (!error.response) {
            console.error('网络异常，请检查连接')
            return Promise.reject('网络异常')
        }

        // 状态码处理
        const status = error.response.status
        if (status === 401) {
            // 未授权跳转登录
            // window.location.href = '/login'
            console.log("need login")
        } else if (status === 403) {
            console.error('无权访问该资源')
        } else if (status >= 500) {
            console.error('服务器错误，请稍后重试')
        }

        return Promise.reject(error)
    }
)

export const get = (url, params) => request.get(url, { params })
export const post = (url, data) => request.post(url, data)
export const put = (url, data) => request.put(url, data)
export const del = (url) => request.delete(url)
export const batchDel = (url,data) => request.delete(url,data)

// 导出实例（用于复杂请求）
export default request



