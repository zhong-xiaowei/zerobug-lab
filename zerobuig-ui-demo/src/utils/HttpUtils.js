import axios from 'axios'

const defHttp = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 300000
});

defHttp.interceptors.response.use(res => {
    return res.data
})

export default defHttp