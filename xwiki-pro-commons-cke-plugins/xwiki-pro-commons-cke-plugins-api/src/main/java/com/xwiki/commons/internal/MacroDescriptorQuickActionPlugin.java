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

/**
 * Representation of a macro descriptor used by the quick action plugins.
 *
 * @version $Id$
 * @since 1.5.0
 */
public class MacroDescriptorQuickActionPlugin
{
    private final String id;

    private String name;

    private String description;

    /**
     * Default constructor.
     *
     * @param id the identifier of the macro.
     */
    public MacroDescriptorQuickActionPlugin(String id)
    {
        this.id = id;
    }

    /**
     * @return the translated name of the macro.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name see {@link #getName()}.
     * @return the current instance
     */
    public MacroDescriptorQuickActionPlugin setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * @return the translated description of the macro
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description see {@link #getDescription()}
     * @return the current instance
     */
    public MacroDescriptorQuickActionPlugin setDescription(String description)
    {
        this.description = description;
        return this;
    }
}
