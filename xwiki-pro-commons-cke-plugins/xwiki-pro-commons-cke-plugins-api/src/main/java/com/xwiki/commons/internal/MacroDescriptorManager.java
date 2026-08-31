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
package com.xwiki.commons.internal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.macro.MacroId;
import org.xwiki.rendering.macro.MacroIdFactory;
import org.xwiki.rendering.macro.MacroLookupException;
import org.xwiki.rendering.macro.MacroManager;
import org.xwiki.rendering.macro.descriptor.MacroDescriptor;
import org.xwiki.rendering.parser.ParseException;

/**
 * Handles the operations over the macro descriptors.
 *
 * @version $Id$
 * @since 1.5.0
 */
@Component(roles = MacroDescriptorManager.class)
@Singleton
public class MacroDescriptorManager
{
    @Inject
    private MacroIdFactory macroIdFactory;

    @Inject
    private MacroManager macroManager;

    @Inject
    private MacroDescriptorQuickActionFactory macroDescriptorQuickActionFactory;

    @Inject
    private Logger logger;

    /**
     * Get a macro descriptor to be used for the configuration of a quick action plugin.
     *
     * @param macroIdAsString the identifier of a macro
     * @return an instance of {@link MacroDescriptor} containing all info for configuring the macro or {@code null} if
     *     it couldn't be found or initialized.
     */
    public MacroDescriptorQuickActionPlugin getMacroDescriptor(String macroIdAsString)
    {
        try {
            MacroId macroId = this.resolveMacroId(macroIdAsString);
            if (macroId != null && this.macroManager.exists(macroId)) {
                return macroDescriptorQuickActionFactory.buildMacroDescriptorQuickActionPlugin(
                    this.macroManager.getMacro(macroId).getDescriptor());
            }
        } catch (MacroLookupException e) {
            this.logger.warn("Failed to lookup macro id [{}]. Root cause is: [{}]", macroIdAsString,
                ExceptionUtils.getRootCauseMessage(e));
        }
        return null;
    }

    private MacroId resolveMacroId(String macroIdAsString)
    {
        try {
            return this.macroIdFactory.createMacroId(macroIdAsString);
        } catch (ParseException e) {
            this.logger.warn("Failed to resolve macro id [{}]. Root cause is: [{}]", macroIdAsString,
                ExceptionUtils.getRootCauseMessage(e));
            return null;
        }
    }
}
