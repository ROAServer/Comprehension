<script setup lang="ts">
import {ElMessage,} from "element-plus";
import {Ref, ref} from "vue";
import {checkPassCode, router} from "~/main";
import {validatePasscode} from "~/http";

interface Form {
  passcode: string
}

const isLoginRunning = ref<boolean>(false);
const loginForm: Ref<Form> = ref<Form>({
  passcode: ""
})

const login = () => {
  isLoginRunning.value = true
  let passcode: string = loginForm.value.passcode
  if (!checkPassCode(passcode)) {
    ElMessage.error("无效的考试码")
  } else {
    validatePasscode(passcode!!).then(b => {
      if (!b) {
        ElMessage.error("无效的考试码")
      } else {
        router.push("/test?passcode=" + passcode)
      }
    }).catch(reason => {
      ElMessage.error("请求出错，请联系管理员")
      console.error(reason)
    })
  }
  isLoginRunning.value = false
}

</script>

<template>
  <el-config-provider namespace="ep">
    <div class="flex main-container">
      <div w="full" py="4">
        <div>
          <el-card id="box" stretch>
            <el-text style="font-weight: 1000">使用测试码登录</el-text>
            <div style="display: flex;margin-top: 20px;flex-direction: row">
              <el-text style="text-align: left;width: 60px">测试码</el-text>
              <el-input placeholder="" style="margin-left: 10px" v-model="loginForm.passcode" @keyup.enter="login"/>
            </div>
            <el-button style="margin-top: 20px" class="button" :loading="isLoginRunning" @click="login">登录</el-button>
          </el-card>
        </div>
      </div>
    </div>
  </el-config-provider>
</template>

<style scoped lang="scss">
#box {
  width: 20rem;
  min-height: 10rem;
  margin: 10rem auto;
  padding: 1rem;

  background: transparent;
  box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.2);
  border-color: transparent;

  @media only screen and (width < 480px) {
    width: 16rem;
  }

  .button {
    align-self: center;
  }
}

#app {
  text-align: center;
  color: var(--ep-text-color-primary);
}

.main-container {
  height: calc(100vh - var(--ep-menu-item-height) - 3px);
}
</style>