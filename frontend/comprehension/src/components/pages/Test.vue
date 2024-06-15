<script setup lang="ts">
import {checkPassCode, router} from "~/main";
import {validatePasscode} from "~/http";
import {ElMessage} from "element-plus";

const params = new URLSearchParams(window.location.search)

const passcode = params.get("passcode")
if (!checkPassCode(passcode)) {
  router.push("/")
}
validatePasscode(passcode!!).then(b => {
  if (!b) {
    router.push("/")
  }
}).catch(reason => {
  ElMessage.error("请求出错，请联系管理员")
  console.error(reason)
})

</script>

<template>
  <el-config-provider namespace="ep">
    <div class="flex main-container">
      <div w="full" py="4">
        <div>
          <el-card id="box" stretch>

          </el-card>
        </div>
      </div>
    </div>
  </el-config-provider>
</template>

<style scoped lang="scss">
#app {
  text-align: center;
  color: var(--ep-text-color-primary);
}

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

.main-container {
  height: calc(100vh - var(--ep-menu-item-height) - 3px);
}
</style>