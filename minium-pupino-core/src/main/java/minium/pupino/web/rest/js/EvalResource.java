/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.pupino.web.rest.js;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Iterables;
import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.script.MiniumScriptEngine;

@Controller
@RequestMapping("/app/rest/js")
public class EvalResource {

    private static final Logger logger = LoggerFactory.getLogger(EvalResource.class);

    @Autowired
    private MiniumScriptEngine engine;

    @RequestMapping(value = "/eval")
    @ResponseBody
    public synchronized EvalResult eval(@RequestParam("expr") final String expression, @RequestParam(value = "lineno", defaultValue = "1") final int lineNumber) {
        try {
            Object result = engine.eval(expression, lineNumber);
            if (result instanceof DebugWebElements) {
                DebugWebElements webElements = (DebugWebElements) result;
                webElements.highlight();
                int totalCount = Iterables.size(webElements);
                return new EvalResult(expression, totalCount);
            } else {
                return new EvalResult(result);
            }
        } catch (Exception e) {
            logger.error("Evaluation of {} failed", expression, e);
            throw new EvalException(e);
        }
    }
    
    /**
     * Clean the scope
     */
    @RequestMapping(value = "/clean",method = POST)
    public synchronized void clean() {
        //TODO
    	logger.info("clean Done");
    }
}
