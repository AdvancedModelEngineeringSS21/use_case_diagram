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
package com.eclipsesource.uml.modelserver;

import org.eclipse.emfcloud.modelserver.common.Routing;
import org.eclipse.emfcloud.modelserver.common.codecs.Codec;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.common.ModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.di.DefaultModelServerModule;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.eclipsesource.uml.modelserver.commands.contributions.AddActorCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddAssociationCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddClassCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddCommentCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddCommentEdgeCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddComponentCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddExtendCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddGeneralizationCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddIncludeCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddPackageCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddPropertyCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.AddUsecaseCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.ChangeBoundsCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.ChangeRoutingPointsCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveActorCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveAssociationCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveClassCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveCommentCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveCommentEdgeCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveComponentCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveExtendCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveExtensionPointCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveGeneralizationCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveIncludeCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemovePackageCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemovePropertyCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.RemoveUsecaseCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetActorNameCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetAssociationEndMultiplicityCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetAssociationEndNameCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetClassNameCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetCommentBodyCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetExtensionPointNameCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetPackageNameCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetPropertyCommandContribution;
import com.eclipsesource.uml.modelserver.commands.contributions.SetUsecaseNameCommandContribution;

public class UmlModelServerModule extends DefaultModelServerModule {

   @Override
   protected Class<? extends ModelResourceManager> bindModelResourceManager() {
      return UmlModelResourceManager.class;
   }

   @Override
   protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
      super.configureEPackages(binding);
      binding.add(UmlPackageConfiguration.class);
      binding.add(UnotationPackageConfiguration.class);
   }

   /**
    * CommandContributions bound in this method will be accessible from the GLSP server.
    */
   @Override
   protected void configureCommandCodecs(final MapBinding<String, CommandContribution> binding) {
      super.configureCommandCodecs(binding);
      // UML Class
      binding.put(AddClassCommandContribution.TYPE, AddClassCommandContribution.class);
      binding.put(RemoveClassCommandContribution.TYPE, RemoveClassCommandContribution.class);
      binding.put(SetClassNameCommandContribution.TYPE, SetClassNameCommandContribution.class);
      // UML Property
      binding.put(AddPropertyCommandContribution.TYPE, AddPropertyCommandContribution.class);
      binding.put(RemovePropertyCommandContribution.TYPE, RemovePropertyCommandContribution.class);
      binding.put(SetPropertyCommandContribution.TYPE, SetPropertyCommandContribution.class);
      // UML Association
      binding.put(AddAssociationCommandContribution.TYPE, AddAssociationCommandContribution.class);
      binding.put(RemoveAssociationCommandContribution.TYPE, RemoveAssociationCommandContribution.class);
      binding.put(SetAssociationEndNameCommandContribution.TYPE, SetAssociationEndNameCommandContribution.class);
      binding.put(SetAssociationEndMultiplicityCommandContribution.TYPE,
         SetAssociationEndMultiplicityCommandContribution.class);
      // ChangeBounds
      binding.put(ChangeBoundsCommandContribution.TYPE, ChangeBoundsCommandContribution.class);
      // ChangeRoutingPoints
      binding.put(ChangeRoutingPointsCommandContribution.TYPE, ChangeRoutingPointsCommandContribution.class);

      /*
       * UML Use Case Diagram
       * HERE: Registering Command Contributions to make them accessible from the outside
       */
      // Package
      binding.put(AddPackageCommandContribution.TYPE, AddPackageCommandContribution.class);
      binding.put(RemovePackageCommandContribution.TYPE, RemovePackageCommandContribution.class);
      binding.put(SetPackageNameCommandContribution.TYPE, SetPackageNameCommandContribution.class);

      // Component
      binding.put(AddComponentCommandContribution.TYPE, AddComponentCommandContribution.class);
      binding.put(RemoveComponentCommandContribution.TYPE, RemoveComponentCommandContribution.class);

      // Actor
      binding.put(AddActorCommandContribution.TYPE, AddActorCommandContribution.class);
      binding.put(RemoveActorCommandContribution.TYPE, RemoveActorCommandContribution.class);
      binding.put(SetActorNameCommandContribution.TYPE, SetActorNameCommandContribution.class);

      // UseCase
      binding.put(AddUsecaseCommandContribution.TYPE, AddUsecaseCommandContribution.class);
      binding.put(RemoveUsecaseCommandContribution.TYPE, RemoveUsecaseCommandContribution.class);
      binding.put(SetUsecaseNameCommandContribution.TYPE, SetUsecaseNameCommandContribution.class);
      binding.put(RemoveExtensionPointCommandContribution.TYPE, RemoveExtensionPointCommandContribution.class);
      binding.put(SetExtensionPointNameCommandContribution.TYPE, SetExtensionPointNameCommandContribution.class);

      // Comment
      binding.put(AddCommentCommandContribution.TYPE, AddCommentCommandContribution.class);
      binding.put(RemoveCommentCommandContribution.TYPE, RemoveCommentCommandContribution.class);
      binding.put(RemoveCommentEdgeCommandContribution.TYPE, RemoveCommentEdgeCommandContribution.class);
      binding.put(SetCommentBodyCommandContribution.TYPE, SetCommentBodyCommandContribution.class);
      binding.put(AddCommentEdgeCommandContribution.TYPE, AddCommentEdgeCommandContribution.class);

      // UML Extend
      binding.put(AddExtendCommandContribution.TYPE, AddExtendCommandContribution.class);
      binding.put(RemoveExtendCommandContribution.TYPE, RemoveExtendCommandContribution.class);

      // UML Include
      binding.put(AddIncludeCommandContribution.TYPE, AddIncludeCommandContribution.class);
      binding.put(RemoveIncludeCommandContribution.TYPE, RemoveIncludeCommandContribution.class);

      // UML Generalization
      binding.put(AddGeneralizationCommandContribution.TYPE, AddGeneralizationCommandContribution.class);
      binding.put(RemoveGeneralizationCommandContribution.TYPE, RemoveGeneralizationCommandContribution.class);

   }

   @Override
   protected void configureCodecs(final MapBinding<String, Codec> binding) {
      super.configureCodecs(binding);
      binding.put(UMLResource.FILE_EXTENSION, UmlCodec.class);
   }

   @Override
   protected void configureRoutings(final MultiBinding<Routing> binding) {
      super.configureRoutings(binding);
      binding.add(UmlModelServerRouting.class);
   }

}
