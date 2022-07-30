<template>
  <div>
    <Captcha @getImage="getImage" @verify="verify"/>
  </div>
</template>

<script>
import defHttp from "@/utils/HttpUtils";
import Captcha from "@/components/sliderCaptcha/Captcha";

export default {
  name: "captcha-demo",
  components: {Captcha},
  data() {
    return {}
  },
  methods: {
    getImage(fun) {
      defHttp.get("/gen").then(res => {
        fun(res)
      })
    },
    verify(id, captchaTrack, fun) {
      // console.log(fun)
      // const reqData = {
      //   id,
      //   captchaTrack,
      //   "form":"123123"
      // }
      // console.log(id)
      // console.log(JSON.stringify(reqData))
      defHttp.post("/check?id=" + id, captchaTrack).then(res => {
        fun(res)
      })
    }
  }
}
</script>

<style scoped>

</style>
