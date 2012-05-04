/*
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/** Navigation */

/**
 * Sets the breadcrumb to whatever the current page is, if this view has page navigation
 * Pages are handled by js on the breadcrumb because the page retrieval happens through
 * ajax
 */
function setPageBreadcrumb(){
	//check to see if page has navigation element, if so show breadcrumb
	if(jq("#Uif-Navigation").html() && jq("#breadcrumbs").length){
		var pageTitle = jq("#currentPageTitle").val();
		var pageId = jq("#pageId").val();
		jq("#breadcrumbs").find("#page_breadcrumb").remove();
		var bcSet = false;
		if(pageTitle && pageTitle != "&nbsp;" && pageTitle != ""){
			jq("#breadcrumbs").append("<li id='page_breadcrumb'><span role='presentation'>&raquo;</span> <span class='kr-current'>" + pageTitle + "</span></li>");
			jq("#current_breadcrumb_span").hide();
            if(jq("#current_breadcrumb_span").parent("li").length){
                jq("#current_breadcrumb_span").unwrap();
            }
            var anchor = jq("#current_breadcrumb_anchor");
            jq("#current_breadcrumb_anchor").wrap("<li/>");
			jq("#current_breadcrumb_anchor").show();
			bcSet = true;
		}
		else if(pageId){
			pageTitle = jq("a[name='"+ escapeName(pageId) + "']").text();
			if(pageTitle && pageTitle != "&nbsp;" && pageTitle != ""){
				jq("#breadcrumbs").append("<li id='page_breadcrumb'><span role='presentation'>&raquo;</span> <span class='kr-current'>" + pageTitle + "</span></li>");
				jq("#current_breadcrumb_span").hide();
                if(jq("#current_breadcrumb_span").parent("li").length){
                    jq("#current_breadcrumb_span").unwrap();
                }
                jq("#current_breadcrumb_anchor").wrap();
				jq("#current_breadcrumb_anchor").show();
				bcSet=true;
			}
		}

		if(!bcSet){
			jq("#current_breadcrumb_anchor").hide();
            if(jq("#current_breadcrumb_anchor").parent("li").length){
                jq("#current_breadcrumb_anchor").unwrap();
            }
            jq("#current_breadcrumb_span").wrap("<li/>");
			jq("#current_breadcrumb_span").show();
		}
	}
}

/**
 * Renders a navigation group for the list with the given id. Helper methods are
 * called based on the type to implement a certain style of navigation.
 *
 * @param listId -
 *          unique id for the unordered list
 * @param navigationType -
 *          the navigation style to render
 */
function createNavigation(listId, navigationType, options) {
	if (navigationType == "VERTICAL_MENU") {
		createVerticalMenu(listId, options);
	}
	else if(navigationType == "TAB_MENU"){
		createTabMenu(listId, options);
	}
}

function createTabMenu(listId, options) {
	jq(document).ready(function(){
		jq("#" + listId).tabMenu(options);
	});
}

/**
 * Uses jQuery menu plug-in to build a menu for the list with the given id
 *
 * @param listId -
 *          unique id for the unordered list
 */
function createVerticalMenu(listId, options) {
	jq(document).ready(function() {
		jq("#" + listId).navMenu(options);
	});
}

/** Widgets */

/**
 * Sets ups a text popout button and window for this particular field that will be generated
 * when that button is clicked
 *
 * @param id - id of the control
 * @param label - label to be used in popout
 * @param summary - summary to be used in popout
 * @param constraint - constraint to be used in popout
 * @param imageUrl - the url for the popout icon
 */
function setupTextPopout(id, label, summary, constraint, imageUrl) {
    var options = {label: label, summary: summary, constraint: constraint};
    jq("#" + id).initPopoutText(options, imageUrl);
}

/**
 * Uses jQuery fancybox to link a fancybox to a given control id. The second
 * argument is a Map of options that are available for the FancyBox. See
 * <link>http://fancybox.net/api</link> for documentation on these options
 *
 * @param controlId -
 *          id for the control that the fancybox should be linked to
 * @param options -
 *          map of option settings (option name/value pairs) for the plugin
 */
function createLightBoxLink(controlId, options) {
    jq(function () {
        var showHistory = false;

        // Check if this is called within a light box
        if (!jq("#fancybox-frame", parent.document).length) {

            // Perform cleanup when lightbox is closed
            options['onCleanup'] = cleanupClosedLightboxForms;

            // If this is not the top frame, then create the lightbox
            // on the top frame to put overlay over whole window
            if (top == self) {
                jq("#" + controlId).fancybox(options);
            } else {
                jq("#" + controlId).click(function (e) {
                    e.preventDefault();
                    options['href'] = jq("#" + controlId).attr('href');
                    top.jQuery.fancybox(options);
                });
            }
        } else {
            jq("#" + controlId).attr('target', '_self');
            showHistory = true;
        }

        // Set the dialogMode = true param
        if (jq("#" + controlId).attr('href').indexOf('&dialogMode=true') == -1) {
            jq("#" + controlId).attr('href', jq("#" + controlId).attr('href') + '&dialogMode=true'
                    + '&showHome=false' + '&showHistory=' + showHistory
                    + '&history=' + jq('#formHistory\\.historyParameterString').val());
        }
    });
}

/**
 * Get post paramaters dynamic Uses
 * jQuery fancybox to create lightbox for lookups. It prevents the default
 * submit and makes an ajax post. The second argument is a Map of options that
 * are available for the FancyBox. See <link>http://fancybox.net/api</link> for
 * documentation on these options
 *
 * @param controlId -
 *          id for the control that the fancybox should be linked to
 * @param options -
 *          map of option settings (option name/value pairs) for the plugin
 */
function createLightBoxPost(controlId, options, actionParameterMapString, lookupReturnByScript) {
    jq(function () {

        // Check if this is not called within a lightbox
        if (!jq("#fancybox-frame", parent.document).length) {
            jq("#" + controlId).click(function (e) {

                // Prevent the default submit
                e.preventDefault();
                jq("[name='jumpToId']").val(controlId);

                // Add the lightBoxCall parameter so that the controller can avoid the redirect
                actionParameterMapString['actionParameters[dialogMode]'] = 'true';
                actionParameterMapString['actionParameters[lightBoxCall]'] = 'true';
                actionParameterMapString['actionParameters[showHistory]'] = 'false';
                actionParameterMapString['actionParameters[showHome]'] = 'false';
                actionParameterMapString['actionParameters[returnByScript]'] = '' + lookupReturnByScript;

                // If this is the top frame, the page is not displayed in the iframeprotlet
                // set the return target
                if (top == self) {
                    actionParameterMapString['actionParameters[returnTarget]'] = '_parent';
                } else {
                    actionParameterMapString['actionParameters[returnTarget]'] = 'iframeportlet';
                }

                // Add the action parameters hidden to form
                for (var key in actionParameterMapString) {
                    writeHiddenToForm(key, actionParameterMapString[key]);
                }

                // Do the Ajax submit on the kualiForm form
                jq("#kualiForm").ajaxSubmit({

                            success: function(data) {
                                // Perform cleanup when lightbox is closed
                                options['onCleanup'] = cleanupClosedLightboxForms;

                                // Add the returned URL to the FancyBox href setting
                                options['href'] = data;

                                // Open the light box
                                if (top == self) {
                                    jq.fancybox(options);
                                } else {
                                    parent.jQuery.fancybox(options);
                                }
                            }
                        });
            });
        } else {

            // Add the action parameters hidden to form and allow the submit action
            jq("#" + controlId).click(function (e) {
                actionParameterMapString['actionParameters[dialogMode]'] = 'true';
                actionParameterMapString['actionParameters[returnTarget]'] = '_self';
                actionParameterMapString['actionParameters[showHistory]'] = 'true';
                actionParameterMapString['actionParameters[showHome]'] = 'false';
                for (var key in actionParameterMapString) {
                    writeHiddenToForm(key, actionParameterMapString[key]);
                }
            });
        }

    });
}

/*
 * Function that returns lookup results by script
 */
function returnLookupResultByScript(fieldName, value) {
    var returnField;
    if (parent.jq == null) {
        returnField = parent.$('#iframeportlet').contents().find('[name="' + escapeName(fieldName) + '"]');
    }else{
        returnField = parent.jq('[name="' + escapeName(fieldName) + '"]');
    }
    returnField.val(value);
    returnField.focus();
    returnField.blur();
    returnField.focus();

    // trigger change event
    returnField.change();
}

/*
 * Function that sets the return target when returning multiple lookup results
 */
function setMultiValueReturnTarget() {
    if (parent.jq == null) {
        jq('#kualiForm').attr('target',parent.$('#iframeportlet').attr('name'));
    }else{
        jq('#kualiForm').attr('target','_parent');
    }
}

/**
 * Opens the inquiry window
 * Is called from the onclick event on the direct inquiry
 * The parameters is added by dynamically getting the values
 * for the fields in the parameter maps and then added to the url string
 *
 * @param url -
 *          the base url to use to call the inquiry
 * @param paramMap -
 *          map of option settings (option name/value pairs) for the plugin
 * @param showLightBox -
 *          flag to indicate if it must be shown in a lightbox
 * @param lightBoxOptions -
 *          map of option settings (option name/value pairs) for the lightbox plugin
 */
function showDirectInquiry(url, paramMap, showLightBox, lightBoxOptions) {

    parameterPairs = paramMap.split(",");
    queryString = "&showHome=false";

    for (i in parameterPairs) {
        parameters = parameterPairs[i].split(":");

        if (jq('[name="' + escapeName(parameters[0]) + '"]').val() == "") {
            alert("Please enter a value in the appropriate field.");
            return false;
        } else {
            queryString = queryString + "&" + parameters[1] + "=" + jq('[name="' + escapeName(parameters[0]) + '"]').val();
        }
    }

    if (showLightBox) {

        // Check if this is called within a light box
        if (!jq("#fancybox-frame", parent.document).length) {

            // Perform cleanup when lightbox is closed
            lightBoxOptions['onCleanup'] = cleanupClosedLightboxForms;

            // If this is not the top frame, then create the lightbox
            // on the top frame to put overlay over whole window
            queryString = queryString + "&showHistory=false&dialogMode=true";
            if (top == self) {
                lightBoxOptions['href'] = url + queryString;
                jq.fancybox(lightBoxOptions);
            } else {
                lightBoxOptions['href'] = url + queryString;
                top.jQuery.fancybox(lightBoxOptions);
            }
        } else {

            // If this is already in a lightbox just open in current lightbox
            queryString = queryString + "&showHistory=true&dialogMode=true";
            window.open(url + queryString, "_self");
        }
    } else {
        queryString = queryString + "&showHistory=false";
        window.open(url + queryString, "_blank", "width=640, height=600, scrollbars=yes");
    }
}

/**
 * Closes the lightbox window
*/
function closeLightbox() {
    top.jQuery.fancybox.close();
}

/**
 * Cleanup form data from server when lightbox window is closed
 */
function cleanupClosedLightboxForms() {
    // get the formKey of the lightbox (fancybox)
    var context = getContext();
    var formKey = context('iframe#fancybox-frame').contents().find('input#formKey').val();

    clearServerSideForm(formKey);
}

/**
 * Uses jQuery DatePicker to render a calendar that can be used to select date
 * values for the field with the given control id. The second argument is a Map
 * of options that are available for the DatePicker. See
 * <link>http://jqueryui.com/demos/datepicker/#option-showOptions</link> for
 * documentation on these options
 *
 * @param controlId -
 *          id for the control that the date picker should populate
 * @param options -
 *          map of option settings (option name/value pairs) for the plugin
 */
function createDatePicker(controlId, options) {
    jq(function() {
        jq("#" + controlId).datepicker(options);
        jq("#" + controlId).datepicker('option','onSelect', function(){jq(this).trigger("focusout");});
    });

    // in order to compensate for jQuery's "Today" functionality (which does not actually return the date to the input box), alter the functionality
    jq.datepicker._gotoToday = function(id) {
        var target = jq(id);
        var inst = this._getInst(target[0]);
        if (this._get(inst, 'gotoCurrent') && inst.currentDay) {
            inst.selectedDay = inst.currentDay;
        inst.drawMonth = inst.selectedMonth = inst.currentMonth;
        inst.drawYear = inst.selectedYear = inst.currentYear;
        }
        else {
            var date = new Date();
            inst.selectedDay = date.getDate();
            inst.drawMonth = inst.selectedMonth = date.getMonth();
            inst.drawYear = inst.selectedYear = date.getFullYear();
        }
        this._notifyChange(inst);
        this._adjustDate(target);

        // The following two lines are additions to the original jQuery code
        this._setDateDatepicker(target, new Date());
        this._selectDate(id, this._getDateDatepicker(target));
    }
}

/**
 * Sets up the script necessary to toggle a group as a disclosure
 *
 * @param groupId -
 *          id for the group to be toggled
 * @param headerId -
 *          id for the group's header in which the toggle link and image will be
 *          inserted
 * @param widgetId - id for the accordion widget, used for updating state
 * @param defaultOpen -
 *          indicates whether the group should be initially open or close
 * @param collapseImgSrc -
 *          path to the image that should be displayed for collapsing the group
 * @param expandImgSrc -
 *          path to the image that should be displayed for expanding the group
 * @param animationSpeed -
 *          speed at which the group should be expanded or collapsed
 * @param renderImage -
 *          boolean that indicates whether the expanded or collapsed image should be rendered
 */
function createDisclosure(groupId, headerId, widgetId, defaultOpen, collapseImgSrc, expandImgSrc, animationSpeed, renderImage) {
    jq(document).ready(function() {
        var groupToggleLinkId = groupId + "_toggle";

        var expandImage = "";
        var collapseImage = "";
        if (renderImage) {
            var expandImage = "<img id='" + groupId + "_exp" + "' src='" + expandImgSrc + "' alt='expand' class='expand_collapse-buttons'/>";
            var collapseImage = "<img id='" + groupId + "_col" + "' src='" + collapseImgSrc + "' alt='collapse' class='expand_collapse-buttons'/>";
        }

        var groupAccordionSpanId = groupId + "_disclosureContent";

        // perform initial open/close and insert toggle link and image
        var headerText = jq("#" + headerId + " > :header, #" + headerId + " > label").html();
        if (defaultOpen) {
            jq("#" + groupAccordionSpanId).slideDown(000);
            headerText = expandImage + headerText;
        }
        else {
            jq("#" + groupAccordionSpanId).slideUp(000);
            headerText = collapseImage + headerText;
        }

        jq("#" + headerId + " > :header, #" + headerId + " > label").html(headerText);
        jq("#" + headerId + " > :header, #" + headerId + " > label").wrap("<a href='#' id='" + groupToggleLinkId + "'>");

        var animationFinishedCallback = function(){
            jQuery("#Uif-Application").attr("data-skipResize", false);
        };
        // perform slide and switch image
        if (defaultOpen) {
            jq("#" + groupToggleLinkId).toggle(
                    function() {
                        jQuery("#Uif-Application").attr("data-skipResize", true);
                        jq("#" + groupAccordionSpanId).slideUp(animationSpeed, animationFinishedCallback);
                        jq("#" + groupId + "_exp").replaceWith(collapseImage);
                        setComponentState(widgetId, 'defaultOpen', false);
                    }, function() {
                        jQuery("#Uif-Application").attr("data-skipResize", true);
                        jq("#" + groupAccordionSpanId).slideDown(animationSpeed, animationFinishedCallback);
                        jq("#" + groupId + "_col").replaceWith(expandImage);
                        setComponentState(widgetId, 'defaultOpen', true);
                    }
            );
        }
        else {
            jq("#" + groupToggleLinkId).toggle(
                    function() {
                        jQuery("#Uif-Application").attr("data-skipResize", true);
                        jq("#" + groupAccordionSpanId).slideDown(animationSpeed, animationFinishedCallback);
                        jq("#" + groupId + "_col").replaceWith(expandImage);
                        setComponentState(widgetId, 'defaultOpen', true);

                    }, function() {
                        jQuery("#Uif-Application").attr("data-skipResize", true);
                        jq("#" + groupAccordionSpanId).slideUp(animationSpeed, animationFinishedCallback);
                        jq("#" + groupId + "_exp").replaceWith(collapseImage);
                        setComponentState(widgetId, 'defaultOpen', false);
                    }
            );
        }
    });
}

/**
 * Expands all the disclosure divs on the page
 */
function expandDisclosures() {
    jq('img[alt="collapse"]').click();
}

/**
 * Collapses all the disclosure divs on the page
 */
function collapseDisclosures() {
    jq('img[alt="expand"]').click();
}

/**
 * Uses jQuery DataTable plug-in to decorate a table with functionality like
 * sorting and page. The second argument is a Map of options that are available
 * for the plug-in. See <link>http://www.datatables.net/usage/</link> for
 * documentation on these options
 *
 * @param tableId -
 *          id for the table that should be decorated
 * @param options -
 *          map of option settings (option name/value pairs) for the plugin
 */
function createTable(tableId, options) {
    jq(document).ready(function() {
        var oTable = jq("#" + tableId).dataTable(options);
        // allow table column size recalculation on window resize
        jq(window).bind('resize', function () {
            oTable.fnAdjustColumnSizing();
        } );
    });
}

/**
 * Select all checkboxes within the collection div that are marked with class 'kr-select-line' (used
 * for multi-value select collections)
 *
 * @param collectionId - id for the collection to select checkboxes for
 */
function selectAllLines(collectionId) {
    jq("#" + collectionId + " input:checkbox.kr-select-line").attr('checked', true);
}

/**
 * Deselects all checkboxes within the collection div that are marked with class 'kr-select-line' (used
 * for multi-value select collections)
 *
 * @param collectionId - id for the collection to deselect checkboxes for
 */
function deselectAllLines(collectionId) {
    jq("#" + collectionId + " input:checkbox.kr-select-line").attr('checked', false);
}

/**
 * Uses jQuery jsTree plug-in to decorate a div with tree functionality. The
 * second argument is a Map of options that are available
 * for the plug-in. See <link>http://www.jstree.com/documentation/</link> for
 * documentation on these options
 *
 * @param divId -
 *          id for the div that should be decorated
 * @param options -
 *          map of option settings (option name/value pairs) for the plugin
 */
function createTree(divId, options) {
    jq(document).ready(function() {
        jq("#" + divId).jstree(options);
    });
}

// Creates tabs for the tabs div id specified, this div is created by tabGroup
function createTabs(id, options) {
    jq("#" + id + "_tabs").tabs(options);
}

/**
 * Uses jQuery UI Auto-complete widget to provide suggest options for the given field. See
 * <link>http://jqueryui.com/demos/autocomplete/</link> for documentation on this widget
 *
 * @param controlId -
 *           id for the html control the autocomplete will be enabled for
 * @param options -
 *           map of option settings (option name/value pairs) for the widget
 * @param queryFieldId -
 *          id for the attribute field the control belongs to, used when making the
 * request to execute the associated attribute query
 * @param queryParameters -
 *         map of parameters that should be sent along with the query. map key gives
 * the name of the parameter to send, and the value gives the name of the field to pull the value from
 */
function createSuggest(controlId, options, queryFieldId, queryParameters) {
    options.source = function(request, response) {
        var queryData = {};
        queryData.methodToCall = 'performFieldSuggest';
        queryData.skipViewInit = 'true';
        queryData.formKey = jq("input#formKey").val();
        queryData.queryTerm = request.term;
        queryData.queryFieldId = queryFieldId;

        for (var parameter in queryParameters) {
            queryData['queryParameter.' + parameter] = coerceValue(queryParameters[parameter]);
        }

        jq.ajax({
                    url: jq("form#kualiForm").attr("action"),
                    dataType: "json",
                    beforeSend: null,
                    complete: null,
                    error: null,
                    data: queryData,
                    success: function (data) {
                        response(data.resultData);
                    }
                });
    };

    jq(document).ready(function() {
        jq("#" + controlId).autocomplete(options);
    });
}

/**
 * Uses the Fluid Reorderer plug-in to allow the items of the given div to be reordered by the user.
 * See <link>http://wiki.fluidproject.org/display/fluid/Grid+Reorderer+API</link> for documentation on the
 * options available for the plug-in
 *
 * @param divId - id for the div containing the items to be reordered
 * @param options - options for reorderer plug-in
 */
function createReorderer(divId, options) {
    fluid.reorderGrid(jq("#" + divId), options);
}

/**
 * Creates the spinner widget for an input
 *
 * @param id - id for the control to apply the spinner to
 * @param options - options for the spinner
 */
function createSpinner(id, options) {
    jq("#" + id).spinit(options);
}

/**
 * Creates the tooltip widget for an component
 *
 * @param id - id for the component to apply the tooltip to
 * @param options - options for the tooltip
 */
function createTooltip(id, text, options, onMouseHoverFlag, onFocusFlag) {
    var elementInfo = getHoverElement(id);
    var element = elementInfo.element;

    options['innerHtml'] = text;
    options['manageMouseEvents'] = false;
    if (onFocusFlag) {
        // Add onfocus trigger
        jQuery("#" + id).focus(function() {
//            if (!jQuery("#" + id).IsBubblePopupOpen()) {
                // TODO : use data attribute to check if control
                if (!isControlWithMessages()) {
                    jQuery("#" + id).SetBubblePopupOptions(options, true);
                    jQuery("#" + id).SetBubblePopupInnerHtml(options.innerHTML, true);
                    jQuery("#" + id).ShowBubblePopup();
                }
//            }
        });
        jQuery("#" + id).blur(function() {
            jQuery("#" + id).HideBubblePopup();
        });
    }
    if (onMouseHoverFlag) {
        // Add mouse hover trigger
        jQuery("#" + id).hover(function() {
            if (!jQuery("#" + id).IsBubblePopupOpen()) {
                if (!isControlWithMessages()) {
                    jQuery("#" + id).SetBubblePopupOptions(options, true);
                    jQuery("#" + id).SetBubblePopupInnerHtml(options.innerHTML, true);
                    jQuery("#" + id).ShowBubblePopup();
                }
            }
        },function(event) {
            if (!onFocusFlag || !jQuery("#" + id).is(":focus")) {
                var result = mouseInTooltipCheck(event, id, element, this, elementInfo.type);
                if (result) {
                    mouseLeaveHideTooltip(id, jQuery("#" + id), element, elementInfo.type);
                }
            }
        });
    }
}

/**
 * Checks if the component is a control that contains validation messages
 * @param id the id of the field
 */
function isControlWithMessages(id) {
    if (jQuery("#" + id).is(".uif-control")) {
        var fieldId = getAttributeId(id);
        var messageData = jQuery("#" + fieldId).data("validationMessages");
        if (messageData) {
            if (messageData.serverErrors.length || messageData.errors.length
                    || messageData.serverWarnings.length || messageData.warnings.length
                    || messageData.serverInfo.length || messageData.info.length) {
                return true;
            }
        }
    }
    return false;
}

/**
 * Workaround to prevent hiding the tooltip when the mouse actually may still be hovering over the field
 * correctly, checks to see if the mouseleave event was entering the tooltip and if so dont continue the
 * hide action, rather add a mouseleave handler that will only be invoked once for that segment, when this
 * is left the check occurs again, until the user has either left the tooltip or the field - then the tooltip
 * is hidden appropriately
 * @param event - mouseleave event
 * @param fieldId - id of the field this logic is being applied to
 * @param triggerElements - the elements that can trigger mouseover
 * @param callingElement - original element that invoked the mouseleave
 * @param type - type of the field
 */
function mouseInTooltipCheck(event, fieldId, triggerElements, callingElement, type) {
    if (event.relatedTarget &&
            jQuery(event.relatedTarget).length &&
            jQuery(event.relatedTarget).attr("class") != null &&
            jQuery(event.relatedTarget).attr("class").indexOf("jquerybubblepopup") >= 0) {
        //this bind is only every invoked once, then unbound - return false to stop hide
        jQuery(event.relatedTarget).one("mouseleave", function (event) {
            mouseInTooltipCheck(event, fieldId, triggerElements, callingElement, type);
        });
        return false;
    }
    //If target moving into is not a triggerElement for this hover
    // and if the source of the event is not a trigger element
    else if (!jQuery(event.relatedTarget).is(triggerElements) && !jQuery(event.target).is(triggerElements)) {
        //hide the tooltip for the original element
        mouseLeaveHideTooltip(fieldId, callingElement, triggerElements, type, true);
        return true;
    }
    else {
        return true;
    }
}

/**
 * Method to hide the tooltip when the mouse leave event was successful for the field
 * @param id id of the field
 * @param currentElement the current element be iterated on
 * @param elements all elements within the hover set
 * @param type type of field
 */
function mouseLeaveHideTooltip(id, currentElement, elements, type, force) {
    var hide = true;
    var tooltipElement = jQuery(currentElement);

    if (type == "fieldset") {
        //hide only if mouseleave is on fieldset not its internal radios/checkboxes
        hide = force || jQuery(currentElement).is("fieldset");
        tooltipElement = elements.filter("label:first");
    }

    //hide only if hide flag is true and the tooltip is open
    if (hide && jQuery(tooltipElement).IsBubblePopupOpen()) {
        hideTooltip(id);
    }
}

/**
 * Hide the tooltip associated with the field by id
 * @param fieldId the id of the field
 */
function hideTooltip(fieldId) {
    var elementInfo = getTooltipElement(fieldId);
    var element = elementInfo.element;
    if (elementInfo.type == "fieldset") {
        //for checkbox/radio fieldsets we put the tooltip on the label of the first input
        element = jQuery(element).filter("label:first");
    }
    var data = jQuery("#" + fieldId).data("validationMessages");
    if (data && data.showTimer) {
        clearTimeout(data.showTimer);
    }
    var tooltipId = jQuery(element).GetBubblePopupID();
    if (tooltipId) {
        //this causes the tooltip to be IMMEDIATELY hidden, rather than wait for animation
        jQuery("#" + tooltipId).css("opacity", 0);
        jQuery("#" + tooltipId).hide();
    }
    jQuery(element).HideBubblePopup();

}

/**
 * Gets the hover elements for a field by id.  The hover elements are the elements which will cause the tooltip to
 * be shown, the element the tooltip is actually placed on is an item its hover elements.
 * @param fieldId the id of the field
 */
function getTooltipElement(fieldId) {
    var hasFieldset = jQuery("#" + fieldId).find("fieldset").length;
    var elementInfo = {};

    if (!hasFieldset) {
        //regular case
        elementInfo.element = jQuery("#" + fieldId);
        elementInfo.type = "";
        if (elementInfo.element.is("input:checkbox")) {
            elementInfo.themeMargins = {
                total:'13px',
                difference:'0px'
            };
        }
    }
    else if (hasFieldset && jQuery("#" + fieldId).find("fieldset > span > input").length) {
        //radio and checkbox fieldset case
        //get the fieldset, the inputs its associated with, and the associated labels as hover elements
        elementInfo.element = jQuery("#" + fieldId).find("fieldset, fieldset input, fieldset label");
        elementInfo.type = "fieldset";
        elementInfo.themeMargins = {
            total:'13px',
            difference:'2px'
        };
    }
    else {
        //not found or wrapping fieldset case
        elementInfo.element = [];
        elementInfo.type = "";
    }
    return elementInfo;
}

/**
 * Executes a query with ajax for the given field to retrieve additional information after
 * the field has been updated (on blur)
 *
 * @param controlId -
 *           id for the html control to pull current value from
 * @param queryFieldId -
 *          id for the attribute field the control belongs to, used when making the
 * request to execute the associated field query
 * @param queryParameters -
 *         map of parameters that should be sent along with the query. map key gives
 * the name of the parameter to send, and the value gives the name of the field to pull the value from
 * @param queryMethodArgs -
 *         list of parameters that should be sent along with the query, the list gives the
 * name of the field in the view to pull values from, and will be sent with the same name
 * as a query parameter on the request
 * @param returnFieldMapping -
 *        map of fields that should be returned (updated) from the query. map key gives
 * the name of the parameter to update, map value is the name of field to pull value from
 */
function executeFieldQuery(controlId, queryFieldId, queryParameters, queryMethodArgs, returnFieldMapping) {
    var queryData = {};

    queryData.methodToCall = 'performFieldQuery';
    queryData.skipViewInit = 'true';
    queryData.formKey = jq("input#formKey").val();
    queryData.queryFieldId = queryFieldId;

    for (var parameter in queryParameters) {
        queryData['queryParameter.' + queryParameters[parameter]] = coerceValue(parameter);
    }

    for (var i = 0; i < queryMethodArgs.length; i++) {
        var parameter = queryMethodArgs[i];
        queryData['queryParameter.' + parameter] = coerceValue(parameter);
    }

    jq.ajax({
                url: jq("form#kualiForm").attr("action"),
                dataType: "json",
                data: queryData,
                beforeSend: null,
                complete: null,
                error: null,
                success: function (data) {
                    // write out return message (or blank)
                    var returnMessageSpan = jq("#" + queryFieldId + "_info_message");
                    if (returnMessageSpan.length > 0) {
                        returnMessageSpan.html(data.resultMessage);
                        if (data.resultMessageStyleClasses) {
                            returnMessageSpan.addClass(data.resultMessageStyleClasses);
                        }
                    }

                    // write out informational field values, note if data does not exist
                    // this will clear the field values
                    for (var returnField in returnFieldMapping) {
                        var fieldValue = data.resultFieldData[returnField];
                        if (!fieldValue) {
                            fieldValue = "";
                        }

                        // check for regular fields
                        var infoFieldSpan = jq("[name='" + escapeName(returnField) + "']");
                        if (infoFieldSpan.length > 0) {
                            infoFieldSpan.val(fieldValue);
                            infoFieldSpan.change();
                        }

                        // check for info spans
                        var returnFieldId = returnField.replace(/\./g, "_")
                                .replace(/\[/g, "-lbrak-")
                                .replace(/\]/g, "-rbrak-")
                                .replace(/\'/g, "-quot-");
                        infoFieldSpan = jq("#" + queryFieldId + "_info_" + returnFieldId);
                        if (infoFieldSpan.length > 0) {
                            infoFieldSpan.html(fieldValue);
                        }
                    }
                }
            });
}