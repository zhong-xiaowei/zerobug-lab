import defHttp from '@/utils/HttpUtils'

const api = {
    save: "/process/designer/save",
    getModel: "/process/model/",
    readXml: "/process/designer/readXml/"
}

export const save = params => defHttp.post(api.save, params)
export const getModel = modelId => defHttp.get(api.getModel + modelId)
export const readXml = modelId => defHttp.get(api.readXml + modelId)