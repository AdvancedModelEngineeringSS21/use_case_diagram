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
package com.eclipsesource.uml.modelserver.commands.semantic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Package;

import com.eclipsesource.uml.modelserver.commands.util.UmlSemanticCommandUtil;

public class RemovePackageCommand extends UmlSemanticElementCommand {

   protected final String semanticUriFragment;

   public RemovePackageCommand(final EditingDomain domain, final URI modelUri, final String semanticUriFragment) {
      super(domain, modelUri);
      this.semanticUriFragment = semanticUriFragment;
   }

   @Override
   protected void doExecute() {
      Package packageToRemove = UmlSemanticCommandUtil.getElement(umlModel, semanticUriFragment, Package.class);
      umlModel.getPackagedElements().remove(packageToRemove);
   }

}
