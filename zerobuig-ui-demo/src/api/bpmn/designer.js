import defHttp from '@/utils/HttpUtils'

const api = {
    test: "/process/designer/test",
    save: "/process/designer/save"
}

export const test = () => defHttp.get(api.test)
export const save = params => defHttp.post(api.save, params)