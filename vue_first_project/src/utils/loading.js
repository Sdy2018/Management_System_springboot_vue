import { ElLoading } from 'element-plus';

let loadingInstance = null; // 移除类型注解，初始值设为null
let requestCount = 0; // 移除类型注解，直接赋初始值

// 显示加载
export const showLoading = (options = {}) => {
    if (!loadingInstance) {
        loadingInstance = ElLoading.service({
            fullscreen: true,
            lock: true,
            text: '加载中...',
            background: 'rgba(201, 201, 201, 0.3)',
            ...options // 合并传入的选项
        });
    }
};

// 补充关闭加载的方法（原代码未完整展示，需补充以避免内存泄漏）
export const hideLoading = () => {
    if (loadingInstance) {
        loadingInstance.close();
        loadingInstance = null;
    }
};