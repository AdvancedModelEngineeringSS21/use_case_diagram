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
import { SChildElement } from "@eclipse-glsp/client";
import {
    boundsFeature,
    Connectable,
    connectableFeature,
    deletableFeature,
    EditableLabel,
    editFeature,
    editLabelFeature,
    fadeFeature,
    hoverFeedbackFeature,
    isEditableLabel,
    layoutableChildFeature,
    layoutContainerFeature,
    Nameable,
    nameFeature,
    popupFeature,
    RectangularNode,
    SEdge,
    selectFeature,
    SLabel,
    SRoutableElement,
    SShapeElement,
    WithEditableLabel,
    withEditLabelFeature
} from "sprotty/lib";

export class LabeledNode extends RectangularNode implements WithEditableLabel, Nameable {
    get editableLabel(): (SChildElement & EditableLabel) | undefined {
        const headerComp = this.children.find(element => element.type === "comp:header");
        if (headerComp) {
            const label = headerComp.children.find(element => element.type === "label:heading");
            if (label && isEditableLabel(label)) {
                return label;
            }
        }
        return undefined;
    }

    get name(): string {
        if (this.editableLabel) {
            return this.editableLabel.text;
        }
        return this.id;
    }
    hasFeature(feature: symbol): boolean {
        return super.hasFeature(feature) || feature === nameFeature || feature === withEditLabelFeature;
    }
}

export class ConnectableEdge extends SEdge implements Connectable {
    canConnect(routable: SRoutableElement, role: "source" | "target"): boolean {
        return true;
        // TODO: If neccessary return false under some conditions
    }

    static readonly DEFAULT_FEATURES = [editFeature, deletableFeature, selectFeature, fadeFeature,
        hoverFeedbackFeature, connectableFeature];

    selected = false;
    hoverFeedback = true;
    opacity = 1;
}

export class ConnectableEditableLabel extends SLabel implements EditableLabel, Connectable {
    constructor() {
        super();
        ConnectableEditableLabel.DEFAULT_FEATURES.push(connectableFeature);
    }
    canConnect(routable: SRoutableElement, role: "source" | "target"): boolean {
        return true;
        // TODO: If neccessary return false under some conditions
    }

    hasFeature(feature: symbol): boolean {
        return feature === editLabelFeature || super.hasFeature(feature);
    }
}

export class ConnectionPoint extends SLabel implements Connectable {
    canConnect(routable: SRoutableElement, role: "source" | "target"): boolean {
        return true;
        // TODO: If neccessary return false under some conditions
    }
    selected = false;
    hoverFeedback = false;
    opacity = 1;
}

export class SEditableLabel extends SLabel implements EditableLabel {
    hasFeature(feature: symbol): boolean {
        return feature === editLabelFeature || super.hasFeature(feature);
    }
}

export class SMultilineEditableLabel extends SLabel implements EditableLabel {
    hasFeature(feature: symbol): boolean {
        return feature === editLabelFeature || super.hasFeature(feature);
    }
    isMultiLine = true;
}

export class Icon extends SShapeElement {
    iconImageName: string;

    hasFeature(feature: symbol): boolean {
        return feature === boundsFeature || feature === layoutContainerFeature || feature === layoutableChildFeature || feature === fadeFeature;
    }
}

export class IconClass extends Icon {
    iconImageName = "Class.svg";
}

export class SLabelNode extends SLabel implements EditableLabel {
    hoverFeedback = false;
    imageName: string;

    hasFeature(feature: symbol): boolean {
        return (feature === selectFeature || feature === editLabelFeature || feature === popupFeature || feature === deletableFeature ||
            feature === hoverFeedbackFeature || super.hasFeature(feature));
    }
}

export class SLabelNodeProperty extends SLabelNode {
    imageName = "Property.svg";
}

// #region UML Use Case Diagram

export class IconPackage extends Icon {
    iconImageName = "Package.gif";
}

export class IconUseCase extends Icon {
    iconImageName = "UseCase.gif";
}

// TODO: LUKAS insert correct icon
export class IconActor extends Icon {
    iconImageName = "Class.svg";
}

// #endregion
