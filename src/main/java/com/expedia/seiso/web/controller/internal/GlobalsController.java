/* 
 * Copyright 2013-2015 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expedia.seiso.web.controller.internal;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.expedia.seiso.conf.CustomProperties;
import com.expedia.seiso.conf.SeisoNav;
import com.expedia.seiso.domain.entity.ConfProp;
import com.expedia.seiso.domain.repo.ConfPropRepo;
import com.expedia.seiso.web.dto.Motd;
import com.expedia.serf.ann.SuppressBasePath;
import com.expedia.serf.web.MediaTypes;

/**
 * @author Willie Wheeler
 */
@RestController
@SuppressBasePath
@RequestMapping("/internal")
public class GlobalsController {
	@Autowired private CustomProperties customProperties;
	@Autowired private ConfPropRepo confPropRepo;
	
	@RequestMapping(
			value = "/globals",
			method = RequestMethod.GET,
			produces = MediaTypes.APPLICATION_HAL_JSON_VALUE)
	public Globals globals() {
		Globals globals = new Globals();
		globals.setMotd(getMotd());
		globals.setSeisoNav(customProperties.getNav());
		globals.setEnableActions(customProperties.getEnableActions());
		return globals;
	}
	
	private Motd getMotd() {
		ConfProp motdProp = confPropRepo.findByKey("motd");
		return (motdProp == null ? null : new Motd(motdProp.getValue()));
	}
	
	@Data
	private static class Globals {
		private SeisoNav seisoNav;
		private Motd motd;
		private Boolean enableActions;
	}
}
