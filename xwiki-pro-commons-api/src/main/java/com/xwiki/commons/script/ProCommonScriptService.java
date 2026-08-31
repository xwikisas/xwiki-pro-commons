/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.xwiki.commons.script;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;
import org.xwiki.script.service.ScriptServiceManager;

/**
 * Pro Commons Script Service. It provides a way to directly use $services.procommons.extraScriptService.
 * @version $Id$
 * @since 1.5.0
 */
@Singleton
@Component
@Named(ProCommonScriptService.HINT)
public class ProCommonScriptService implements ScriptService
{
    /**
     * Component hint.
     */
    public static final String HINT = "procommons";

    @Inject
    private ScriptServiceManager scriptServiceManager;

    /**
     * @param serviceName name of a script service related to pro commons.
     * @return the ScriptService with the given name.
     */
    public ScriptService get(String serviceName)
    {
        return scriptServiceManager.get(HINT + '.' + serviceName);
    }
}
