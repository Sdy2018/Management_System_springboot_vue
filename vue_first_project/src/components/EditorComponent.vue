<!-- EditorComponent.vue -->
<template>
  <div v-if="visible" style="border: 1px solid #ccc">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="data.localValue"
        :defaultConfig="editorConfig"
        @onCreated="handleCreated"
    />
  </div>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import {onBeforeUnmount, onMounted, reactive, ref, shallowRef, watch} from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';

// 1. 声明事件
const emit = defineEmits(['update:content']); // ✅ 关键步骤

const props = defineProps({
  visible: Boolean, // 控制编辑器挂载/销毁
  content: String   // 初始内容
});

const data=reactive({
  localValue:""
})

let localValue = ref()
const editorRef = shallowRef(); // 必须用 shallowRef

// 内容变化时同步到父组件
watch(()=>data.localValue, (val) =>{
      emit('update:content', val)
}
);

// 内容变化时同步到父组件
watch( ()=>props.content, (newVal) => {
  data.localValue = newVal ?? '<p></p>';
}, { immediate: true });

// 销毁实例
onBeforeUnmount(() => editorRef.value?.destroy());

onMounted(() => {
  data.localValue = props.content // 缺失此代码会导致初始值不同步
})



const toolbarConfig = {}
const editorConfig = {
  placeholder: '请输入内容...' ,
  MENU_CONF:{
    uploadImage:{
      server:import.meta.env.VITE_API_BASE_URL+'/api/files/wangEditor/upload',
      fieldName: 'file',
      onSuccess(file, res) {
        console.log(`${file.name} 上传成功`, res.data.url)
      },
    },
  }
}


// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}


</script>