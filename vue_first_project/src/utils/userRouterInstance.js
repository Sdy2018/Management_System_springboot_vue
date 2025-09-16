import { useRouter } from 'vue-router';

export function useRouterInstance() {
    const router = useRouter();
    return {
        push: (path) => router.push(path),
        replace: (path) => router.replace(path),
        // 可扩展其他方法：goBack, navigateTo 等
    };
}