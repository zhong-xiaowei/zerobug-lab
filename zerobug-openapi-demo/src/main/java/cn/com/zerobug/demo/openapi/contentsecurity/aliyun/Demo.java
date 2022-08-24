package cn.com.zerobug.demo.openapi.contentsecurity.aliyun;

import com.aliyun.tea.*;
import com.aliyun.imageaudit20191230.*;
import com.aliyun.imageaudit20191230.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.*;
import com.aliyun.teautil.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/21
 */
public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    public static com.aliyun.imageaudit20191230.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId("LTAI4G3hvu8qm1ibBEfSrggr")
                // 您的 AccessKey Secret
                .setAccessKeySecret("982iEl3cwvp7OJybwRFlbuQgWyeSDh");
        // 访问的域名
        config.endpoint = "imageaudit.cn-shanghai.aliyuncs.com";
        return new com.aliyun.imageaudit20191230.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.imageaudit20191230.Client client = Demo.createClient("accessKeyId", "accessKeySecret");
        ScanTextRequest.ScanTextRequestLabels labels0 = new ScanTextRequest.ScanTextRequestLabels().setLabel("ad");
        ScanTextRequest.ScanTextRequestLabels labels1 = new ScanTextRequest.ScanTextRequestLabels().setLabel("abuse");
        ScanTextRequest.ScanTextRequestLabels labels2 = new ScanTextRequest.ScanTextRequestLabels().setLabel("terrorism");
        ScanTextRequest.ScanTextRequestLabels labels3 = new ScanTextRequest.ScanTextRequestLabels().setLabel("politics");
        ScanTextRequest.ScanTextRequestTasks tasks0 = new ScanTextRequest.ScanTextRequestTasks().setContent("我草拟吗的，给我加微信");
        ScanTextRequest scanTextRequest = new ScanTextRequest().setTasks(java.util.Arrays.asList(tasks0)).setLabels(java.util.Arrays.asList(labels0, labels1, labels2, labels3));
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            ScanTextResponse scanTextResponse = client.scanTextWithOptions(scanTextRequest, runtime);
            var results = scanTextResponse.getBody().getData().getElements().get(0).getResults();
            ScanTextResponseBody.ScanTextResponseBodyDataElementsResults scanTextResponseBodyDataElementsResults = results.get(0);
            List<ScanTextResponseBody.ScanTextResponseBodyDataElementsResultsDetails> details = scanTextResponseBodyDataElementsResults.getDetails();
            for (ScanTextResponseBody.ScanTextResponseBodyDataElementsResultsDetails detail : details) {
                List<ScanTextResponseBody.ScanTextResponseBodyDataElementsResultsDetailsContexts> contexts = detail.getContexts();
                List<String> collect = contexts.stream().map(ScanTextResponseBody.ScanTextResponseBodyDataElementsResultsDetailsContexts::getContext)
                        .collect(Collectors.toList());
                LOGGER.info("{}->>>{}", detail.getLabel(),collect);
            }
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }

}
