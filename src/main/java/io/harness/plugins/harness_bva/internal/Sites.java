package io.harness.plugins.harness_bva.internal;


import net.sf.json.JSONObject;
import org.kohsuke.stapler.AnnotationHandler;
import org.kohsuke.stapler.InjectedParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.servlet.ServletException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@InjectedParameter(Sites.PayloadHandler.class)
public @interface Sites {
    class PayloadHandler extends AnnotationHandler<Sites> {
        @Override
        public Map<String,List<JobConfig>> parse(StaplerRequest req, Sites a, Class type, String pName) throws ServletException {
            if (!req.getMethod().equals("POST")) {
                return Collections.emptyMap();
            }

            JSONObject sites = req.getSubmittedForm();
            List<JobConfig> managed = req.bindJSONToList(JobConfig.class, sites.get("sites"));
            List<JobConfig> buildConfigs = req.bindJSONToList(JobConfig.class, sites.get("bc"));
            List<JobConfig> deploymentConfigs = req.bindJSONToList(JobConfig.class, sites.get("dc"));
            List<JobConfig> rollbackConfigs = req.bindJSONToList(JobConfig.class, sites.get("rc"));

            Map<String,List<JobConfig>> m = new HashMap<>();
            m.put("managed", managed);
            m.put("build", buildConfigs);
            m.put("deployment", deploymentConfigs);
            m.put("rollback", rollbackConfigs);
            return m;
        }
    }
}