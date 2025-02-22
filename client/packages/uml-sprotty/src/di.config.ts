/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import "@eclipse-glsp/client/css/glsp-sprotty.css";
import "sprotty/css/edit-label.css";

import {
    boundsModule,
    buttonModule,
    configureModelElement,
    configureViewerOptions,
    ConsoleLogger,
    copyPasteContextMenuModule,
    decorationModule,
    defaultGLSPModule,
    defaultModule,
    DeleteElementContextMenuItemProvider,
    edgeLayoutModule,
    expandModule,
    exportModule,
    fadeModule,
    glspCommandPaletteModule,
    glspContextMenuModule,
    glspEditLabelModule,
    GLSPGraph,
    glspHoverModule,
    glspMouseToolModule,
    glspSelectModule,
    glspServerCopyPasteModule,
    HtmlRoot,
    HtmlRootView,
    labelEditModule,
    labelEditUiModule,
    layoutCommandsModule,
    LogLevel,
    modelHintsModule,
    modelSourceModule,
    openModule,
    PolylineEdgeView,
    routingModule,
    saveModule,
    SCompartment,
    SCompartmentView,
    SGraphView,
    SLabel,
    SLabelView,
    SRoutingHandle,
    SRoutingHandleView,
    toolFeedbackModule,
    toolsModule,
    TYPES,
    validationModule,
    viewportModule,
    zorderModule
} from "@eclipse-glsp/client/lib";
import executeCommandModule from "@eclipse-glsp/client/lib/features/execute/di.config";
import { Container, ContainerModule } from "inversify";
import { EditLabelUI } from "sprotty/lib";

import { EditLabelUIAutocomplete } from "./features/edit-label";
import umlToolPaletteModule from "./features/tool-palette/di.config";
import { LabelSelectionFeedback } from "./feedback";
import {
    ConnectableEdge,
    ConnectableEditableLabel,
    ConnectionPoint,
    IconActor,
    IconClass,
    IconPackage,
    IconUseCase,
    LabeledNode,
    SEditableLabel,
    SLabelNodeProperty,
    SMultilineEditableLabel
} from "./model";
import { BaseTypes, UmlTypes } from "./utils";
import {
    ActorNodeView,
    ClassNodeView,
    CommentNodeView,
    DirectedEdgeView,
    IconView,
    LabelNodeView,
    PackageNodeView,
    UseCaseNodeView
} from "./views";

export default (containerId: string): Container => {
    const classDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
        rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
        rebind(TYPES.LogLevel).toConstantValue(LogLevel.info);
        rebind(EditLabelUI).to(EditLabelUIAutocomplete);
        bind(TYPES.IContextMenuItemProvider).to(DeleteElementContextMenuItemProvider);

        const context = { bind, unbind, isBound, rebind };
        bind(TYPES.IVNodePostprocessor).to(LabelSelectionFeedback);
        configureModelElement(context, BaseTypes.GRAPH, GLSPGraph, SGraphView);
        configureModelElement(context, BaseTypes.HTML, HtmlRoot, HtmlRootView);
        configureModelElement(context, UmlTypes.CLASS, LabeledNode, ClassNodeView);
        configureModelElement(context, UmlTypes.LABEL_NAME, SEditableLabel, SLabelView);
        configureModelElement(context, UmlTypes.LABEL_EDGE_NAME, SEditableLabel, SLabelView);
        configureModelElement(context, UmlTypes.LABEL_EDGE_MULTIPLICITY, SEditableLabel, SLabelView);
        configureModelElement(context, UmlTypes.PROPERTY, SLabelNodeProperty, LabelNodeView);
        configureModelElement(context, UmlTypes.LABEL_TEXT, SLabel, SLabelView);
        configureModelElement(context, BaseTypes.COMPARTMENT, SCompartment, SCompartmentView);
        configureModelElement(context, BaseTypes.COMPARTMENT_HEADER, SCompartment, SCompartmentView);
        configureModelElement(context, UmlTypes.ICON_CLASS, IconClass, IconView);
        configureModelElement(context, UmlTypes.LABEL_ICON, SLabel, SLabelView);
        configureModelElement(context, BaseTypes.ROUTING_POINT, SRoutingHandle, SRoutingHandleView);
        configureModelElement(context, BaseTypes.VOLATILE_ROUTING_POINT, SRoutingHandle, SRoutingHandleView);
        configureModelElement(context, UmlTypes.ASSOCIATION, ConnectableEdge, PolylineEdgeView);

        // #region UML USE CASE DIAGRAM
        configureModelElement(context, UmlTypes.PACKAGE, LabeledNode, PackageNodeView);
        configureModelElement(context, UmlTypes.ICON_PACKAGE, IconPackage, IconView);
        configureModelElement(context, UmlTypes.COMPONENT, LabeledNode, PackageNodeView);
        configureModelElement(context, UmlTypes.COMMENT, LabeledNode, CommentNodeView);
        configureModelElement(context, UmlTypes.COMMENT_BODY, SMultilineEditableLabel, SLabelView);
        configureModelElement(context, UmlTypes.ACTOR, LabeledNode, ActorNodeView);
        configureModelElement(context, UmlTypes.ICON_ACTOR, IconActor, IconView);
        configureModelElement(context, UmlTypes.USECASE, LabeledNode, UseCaseNodeView);
        configureModelElement(context, UmlTypes.ICON_USECASE, IconUseCase, IconView);
        configureModelElement(context, UmlTypes.EXTENSIONPOINT, ConnectableEditableLabel, SLabelView);
        configureModelElement(context, UmlTypes.INCLUDE, ConnectableEdge, DirectedEdgeView);
        configureModelElement(context, UmlTypes.EXTEND, ConnectableEdge, DirectedEdgeView);
        configureModelElement(context, UmlTypes.GENERALIZATION, ConnectableEdge, DirectedEdgeView);
        configureModelElement(context, UmlTypes.CONNECTIONPOINT, ConnectionPoint, SLabelView);

        // #endregion

        configureViewerOptions(context, {
            needsClientLayout: true,
            baseDiv: containerId
        });
    });

    const container = new Container();
    container.load(decorationModule, validationModule, defaultModule, glspMouseToolModule, defaultGLSPModule, glspSelectModule, boundsModule, viewportModule, toolsModule,
        glspHoverModule, fadeModule, exportModule, expandModule, openModule, buttonModule, modelSourceModule, labelEditModule, labelEditUiModule, glspEditLabelModule,
        classDiagramModule, saveModule, executeCommandModule, toolFeedbackModule, modelHintsModule, glspContextMenuModule, glspServerCopyPasteModule,
        copyPasteContextMenuModule, glspCommandPaletteModule, umlToolPaletteModule, routingModule, edgeLayoutModule, zorderModule,
        layoutCommandsModule);

    return container;

};
