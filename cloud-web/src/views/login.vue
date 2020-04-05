<template>
    <div class="login">
        <el-form :model="loginForm" :rules="loginRules" class="login-form" label-position="left" label-width="0px" ref="loginForm">
            <h3 class="title">
                后台管理系统
            </h3>
            <el-form-item prop="username">
                <el-input auto-complete="off" placeholder="账号" type="text" v-model="loginForm.username">
                    <svg-icon class="el-input__icon input-icon" icon-class="user" slot="prefix"/>
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input @keyup.enter.native="handleLogin" auto-complete="off" placeholder="密码" type="password" v-model="loginForm.password">
                    <svg-icon class="el-input__icon input-icon" icon-class="password" slot="prefix"/>
                </el-input>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: 'Login',
        data() {
            return {
                loginForm: {
                    username: null,
                    password: null
                },
                loading: false,
                loginRules: {
                    username: [{required: true, trigger: 'blur', message: '用户名不能为空'}],
                    password: [{required: true, trigger: 'blur', message: '密码不能为空'}],
                }
            }
        },
        methods: {
            handleLogin() {
                this.$refs.loginForm.validate(valid => {
                    const user = {
                        username: this.loginForm.username,
                        password: this.loginForm.password
                    };
                    if (valid) {
                        this.loading = true;
                        this.$store.dispatch('Login', user)
                            .then(() => {
                                this.loading = false;
                                this.$router.push({path: this.redirect || '/'})
                            })
                            .catch(() => {
                                this.loading = false;
                            })
                    } else {
                        console.log('登录表单参数校验不通过');
                        return false
                    }
                })
            }
        }
    };

</script>

<style lang="scss" rel="stylesheet/scss">
    .login {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
        background-image: url(https://api.isoyu.com/bing_images.php);
        background-size: cover;
    }

    .title {
        margin: 0 auto 30px auto;
        text-align: center;
        color: #707070;
    }

    .login-form {
        border-radius: 6px;
        background: #ffffff;
        width: 385px;
        padding: 25px 25px 5px 25px;

        .el-input {
            height: 38px;

            input {
                height: 38px;
            }
        }

        .input-icon {
            height: 39px;
            width: 14px;
            margin-left: 2px;
        }
    }

    .login-tip {
        font-size: 13px;
        text-align: center;
        color: #bfbfbf;
    }
</style>
