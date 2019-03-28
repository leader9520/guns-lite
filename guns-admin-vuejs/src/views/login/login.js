
import { isvalidUsername } from '@/utils/validate'
import LangSelect from '@/components/LangSelect'
import { Encrypt, Decrypt, Md5 } from '@/utils/cryptojs'

export default {
  name: 'login',
  components: { LangSelect },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 4) {
        callback(new Error('The password can not be less than 4 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginForm2: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      pwdType: 'password'
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.loginForm2.password = Md5(this.loginForm.password)
      console.log(this.loginForm2.password.substr(0, 16))
      this.loginForm2.username = Encrypt(this.loginForm.username, this.loginForm2.password.substr(0, 16))
      console.log(this.loginForm2.username)
      console.log(Decrypt(this.loginForm2.username, this.loginForm2.password.substr(0, 16)))
      console.log(this.loginForm2.password)
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('Login', this.loginForm2).then(() => {
            this.loading = false
            this.$router.push({ path: '/' })
          }).catch((err) => {
            this.$message({
              message: err,
              type: 'error'
            })
            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
