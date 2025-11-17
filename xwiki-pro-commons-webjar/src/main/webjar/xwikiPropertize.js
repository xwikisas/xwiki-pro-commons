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
define('xwiki-propertize', ['jquery'], function ($) {
  var textareaToInputList = function (textarea, config) {
    textarea.hide();
    var configuration = getConfig(textarea, config);
    var textareaParent = textarea.parent();
    var propertiesParent = $('<div class="properties"/>');
    textareaParent.append(propertiesParent);
    $.each(configuration.properties, function (key, value) {
      createPropertyEntry(value, configuration, propertiesParent);
    });
    var addPropertyButton = $('<span class="addProperty"/>');
    addPropertyButton
      .html((configuration.addproperty && configuration.addproperty.label) || 'Add Property');
    textareaParent.append(addPropertyButton);
    bindPropertyEvents(textarea, propertiesParent, configuration);
    return propertiesParent;
  };

  var getConfig = function (element, config) {
    let mergedConfig = { ...config };
    mergedConfig.content = mergedConfig.content || element.val();
    Object.assign(mergedConfig, element.data());
    mergedConfig.separator = mergedConfig.separator || '|';
    mergedConfig.properties = mergedConfig.content.split(mergedConfig.separator);
    return mergedConfig;
  };
  var createPropertyEntry = function (property, config, parent) {
    var propertyValues = property.split('=');
    var xWikiValue = propertyValues[0] || '';
    // Preserve potential '=' characters in the value.
    var aDValue = property.replace(xWikiValue + '=', '') || '';
    var keyInput = $('<input/>').attr({
      'type': 'text',
      'class': 'key',
      'value': xWikiValue,
      'placeholder': config.keyTip
    });
    if (config.suggest && config.suggest.script) {
      var keyInputSuggest = new XWiki.widgets.Suggest(keyInput.get(0), config.suggest);
    }

    var valueInput = $('<input/>').attr({
      'type': 'text',
      'class': 'value',
      'value': aDValue.replaceAll('\\\\', '\\'),
      'placeholder': config.valueTip
    });
    var inputsWrapper = $('<div class="property"/>');
    var removePropertyButton = $('<span class="removeProperty"/>');
    inputsWrapper
      .append(keyInput)
      .append(' \u2192 ')
      .append(valueInput)
      .append(removePropertyButton);
    parent.append(inputsWrapper);
  };

  var bindPropertyEvents = function (textarea, propertiesParent, config) {
    $(propertiesParent).on('click', '.removeProperty', function () {
      var property = $(this).parent();
      property.remove();
      updateTextarea(textarea, propertiesParent, config);
    });
    $(propertiesParent).parent().find('.addProperty').on('click', function () {
      createPropertyEntry('', config, propertiesParent);
      updateTextarea(textarea, propertiesParent, config);
    });
    $(propertiesParent).on('focus', 'input', function () {
      updateTextarea(textarea, propertiesParent, config);
    }).on('focusout', 'input', function () {
      updateTextarea(textarea, propertiesParent, config);
    });
  };

  var updateTextarea = function (textarea, propertiesWrapper, config) {
    let newContent = propertiesWrapper.find(".property")
      .filter(function () {
        const key = $(this).find(".key").val().trim();
        const value = $(this).find(".value").val().trim();
        return key && value;
      })
      .map(function () {
        const key = $(this).find(".key").val();
        const value = $(this).find(".value").val();
        return key + "=" + value;
      }).get().join(config.separator);
    textarea.text(newContent);
  };

  var destroy = function (textarea, propertiesParent) {
    textarea.show();
    propertiesParent.remove();
    delete textarea[0].xwikiPropertize;
  };

  /**
   * Config:
   * {
   *   content: "smth=smthelse|dasda=wqqfd", // the initial content of the properties displayer.
   *   separator: "|", // the separator for each key-value pair.
   *   addProperty: {
   *     "label": "add new entry" // The label to be used for the "Add Property" button.
   *   },
   *   keyTip: "The key of the entry", // The placeholder for the key input.
   *   valueTip: "The value of the entry", // The placeholder for the value input.
   *   suggest: { // The configuration for the suggest widget.
   *     script: "http://adadasd",
   *     varname: 'q',
   *     noresults: 'No results',
   *     json: true,
   *     resultsParameter: 'results',
   *     resultValue: 'value'
   *   }
   */
  $.fn.xwikiPropertize = function (config) {
    var firstElement = this[0];
    if (firstElement.xwikiPropertize) {
      return this;
    }
    let propertiesElement = textareaToInputList(this, config);
    firstElement.xwikiPropertize = {
      config: config,
      element: propertiesElement,
      destroy: function () {
        destroy(this, propertiesElement);
      }
    };
    return this;
  };
});