package com.dummy.myerp.business;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) throws FunctionalException {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("com/dummy/myerp/business/applicationContext.xml");

		BusinessProxy businessProxy = (BusinessProxy) context.getBean("BusinessProxy");

		List<CompteComptable> compteComptables = businessProxy.getComptabiliteManager().getListCompteComptable();
//		System.out.println("Codes comptable:");
//		for (CompteComptable compteComptable : compteComptables) {
//			System.out.println(compteComptable.toString());
//		}

		List<JournalComptable> journalComptableList = businessProxy.getComptabiliteManager().getListJournalComptable();
//		System.out.println("Journaux comptables:");
//		for (JournalComptable journalComptable : journalComptableList) {
//			System.out.println(journalComptable.toString());
//		}

		List<EcritureComptable> ecritureComptableList = businessProxy.getComptabiliteManager().getListEcritureComptable();
		System.out.println("Ecritures comptables 36 : ");
		for (EcritureComptable ecritureComptable : ecritureComptableList) {
			System.out.println(ecritureComptable);
		}

		JournalComptable journalComptable = journalComptableList.get(0);
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setDate(new Date());
		ecritureComptable.setLibelle("Libellé");
		ecritureComptable.setJournal(journalComptable);
		LigneEcritureComptable l1 = new LigneEcritureComptable();
		l1.setDebit(BigDecimal.valueOf(100));
		l1.setCompteComptable(compteComptables.get(0));
		l1.setLibelle("Facture Fournisseur");

		LigneEcritureComptable l2 = new LigneEcritureComptable();
		l2.setCredit(BigDecimal.valueOf(100));
		l2.setCompteComptable(compteComptables.get(4));
		l2.setLibelle("Paiement Banque");

		ecritureComptable.getListLigneEcriture().add(l1);
		ecritureComptable.getListLigneEcriture().add(l2);

		businessProxy.getComptabiliteManager().addReference(ecritureComptable);
		ecritureComptable.setLibelle("Libellé : " + ecritureComptable.getReference());
		businessProxy.getComptabiliteManager().insertEcritureComptable(ecritureComptable);

		ecritureComptableList = businessProxy.getComptabiliteManager().getListEcritureComptable();
		System.out.println("Ecritures comptables 62 : ");
		for (EcritureComptable e : ecritureComptableList) {
			System.out.println(e);
		}

		// Termine le contexte Spring
		context.close();

	}
}
