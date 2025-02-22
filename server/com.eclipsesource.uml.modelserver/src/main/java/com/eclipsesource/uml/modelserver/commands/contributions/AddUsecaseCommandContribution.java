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
package com.eclipsesource.uml.modelserver.commands.contributions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.glsp.graph.GPoint;

import com.eclipsesource.uml.modelserver.commands.compound.AddUsecaseCompoundCommand;
import com.eclipsesource.uml.modelserver.commands.util.UmlNotationCommandUtil;
import com.eclipsesource.uml.modelserver.commands.util.UmlPositioningAndSizingUtil;
import com.eclipsesource.uml.modelserver.unotation.Shape;

/**
 * Command Contribution is responsible for registering commands on the modelserver so that the GLSP server can access
 * them.
 *
 * @author winterleitner
 *
 */
public class AddUsecaseCommandContribution extends UmlCompoundCommandContribution {

   public static final String TYPE = "addUsecaseContributuion";

   /**
    * Add a new UseCase in the model root.
    *
    * @param position
    * @return
    */
   public static CCompoundCommand create(final GPoint position) {
      CCompoundCommand addUsecaseCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addUsecaseCommand.setType(TYPE);
      addUsecaseCommand.getProperties().put(UmlNotationCommandContribution.POSITION_X, String.valueOf(position.getX()));
      addUsecaseCommand.getProperties().put(UmlNotationCommandContribution.POSITION_Y, String.valueOf(position.getY()));
      return addUsecaseCommand;
   }

   /**
    * Add a new Usecase inside a parent package
    *
    * @param position
    * @param parentSemanticUri
    * @return
    */
   public static CCompoundCommand create(final GPoint position, final String parentSemanticUri) {
      CCompoundCommand addUsecaseCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addUsecaseCommand.setType(TYPE);
      addUsecaseCommand.getProperties().put(UmlNotationCommandContribution.POSITION_X, String.valueOf(position.getX()));
      addUsecaseCommand.getProperties().put(UmlNotationCommandContribution.POSITION_Y, String.valueOf(position.getY()));
      addUsecaseCommand.getProperties().put(PARENT_SEMANTIC_URI_FRAGMENT, parentSemanticUri);
      return addUsecaseCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      if (command.getProperties().containsKey(PARENT_SEMANTIC_URI_FRAGMENT)) {
         String parentUri = command.getProperties().get(PARENT_SEMANTIC_URI_FRAGMENT);
         GPoint p = ((Shape) UmlNotationCommandUtil.getNotationElement(modelUri, domain, parentUri)).getPosition();
         GPoint usecasePosition = UmlNotationCommandUtil.getGPoint(
            command.getProperties().get(UmlNotationCommandContribution.POSITION_X),
            command.getProperties().get(UmlNotationCommandContribution.POSITION_Y));
         usecasePosition = UmlPositioningAndSizingUtil.getRelativePosition(p, usecasePosition);

         return new AddUsecaseCompoundCommand(domain, modelUri, usecasePosition, parentUri);
      }

      GPoint usecasePosition = UmlNotationCommandUtil.getGPoint(
         command.getProperties().get(UmlNotationCommandContribution.POSITION_X),
         command.getProperties().get(UmlNotationCommandContribution.POSITION_Y));
      return new AddUsecaseCompoundCommand(domain, modelUri, usecasePosition);
   }

}
