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

import org.xwiki.component.annotation.Component;
import org.xwiki.localization.ContextualLocalizationManager;
import org.xwiki.rendering.macro.descriptor.MacroDescriptor;

/**
 * Factory in charge of building a {@link MacroDescriptorQuickActionPlugin} based on a {@link MacroDescriptor}.
 *
 * @version $Id$
 * @since 1.5.0
 */
@Component(roles = { MacroDescriptorQuickActionFactory.class })
@Singleton
public class MacroDescriptorQuickActionFactory
{
    private static final String DOT_NAME = ".name";

    private static final String DOT_DESCRIPTION = ".description";

    @Inject
    private ContextualLocalizationManager localizationManager;

    /**
     * Build a {@link MacroDescriptorQuickActionPlugin} based on the information provided by the
     * {@link MacroDescriptor}.
     *
     * @param macroDescriptor the descriptor of the macro
     * @return an instance of {@link MacroDescriptorQuickActionPlugin}
     */
    public MacroDescriptorQuickActionPlugin buildMacroDescriptorQuickActionPlugin(MacroDescriptor macroDescriptor)
    {
        String macroTranslationKey = "rendering.macro." + macroDescriptor.getId();
        return new MacroDescriptorQuickActionPlugin(macroDescriptor.getId().getId()).setName(
            getParameterTranslation(macroTranslationKey + DOT_NAME, macroDescriptor.getName())).setDescription(
            getParameterTranslation(macroTranslationKey + DOT_DESCRIPTION, macroDescriptor.getDescription()));
    }

    private String getParameterTranslation(String translationKey, String fallback)
    {
        String result = this.localizationManager.getTranslationPlain(translationKey);
        return (result != null) ? result : fallback;
    }
}
