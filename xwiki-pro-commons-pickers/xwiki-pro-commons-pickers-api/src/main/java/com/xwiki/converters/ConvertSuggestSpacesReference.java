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
package com.xwiki.converters;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.reference.SpaceReferenceResolver;
import org.xwiki.properties.converter.Converter;

import com.xwiki.pickers.SuggestSpacesReference;

/**
 * Converter for compatibility with wikimacro API.
 *
 * @version $Id$
 */
@Singleton
@Component

public class ConvertSuggestSpacesReference implements Converter<SuggestSpacesReference>
{
    @Inject
    private SpaceReferenceResolver<String> spaceReferenceResolver;

    @Override
    public SuggestSpacesReference convert(Type targetType, Object sourceValue)
    {
        return new SuggestSpacesReference(sourceValue.toString(), spaceReferenceResolver);
    }
}
