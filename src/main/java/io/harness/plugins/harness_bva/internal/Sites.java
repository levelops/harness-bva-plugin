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
import java.util.ArrayList;
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
        public Map<String,List<UpdateSite>> parse(StaplerRequest req, Sites a, Class type, String pName) throws ServletException {
            if (!req.getMethod().equals("POST")) {
                return Collections.emptyMap();
            }

            JSONObject sites = req.getSubmittedForm();
            List<UpdateSite> managed = req.bindJSONToList(UpdateSite.class, sites.get("sites"));
            List<UpdateSite> buildConfigs = req.bindJSONToList(UpdateSite.class, sites.get("bc"));
            List<UpdateSite> deploymentConfigs = req.bindJSONToList(UpdateSite.class, sites.get("dc"));
            List<UpdateSite> rollbackConfigs = req.bindJSONToList(UpdateSite.class, sites.get("rc"));

            Map<String,List<UpdateSite>> m = new HashMap<>();
            m.put("managed", managed);
            m.put("build", buildConfigs);
            m.put("deployment", deploymentConfigs);
            m.put("rollback", rollbackConfigs);
            return m;
        }
    }
}