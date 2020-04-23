package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.util.List;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.JournalComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

import static com.dummy.myerp.consumer.utils.Constants.*;

/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();

    private String sqlGetListCompteComptable;
    private String sqlSelectSequenceEcritureComptable;
    private String sqlInsertSequenceEcritureComptable;
    private String sqlUpdateSequenceEcritureComptable;
    private String sqlGetListJournalComptable;
    private String sqlGetListEcritureComptable;
    private String sqlGetEcritureComptable;
    private String sqlGetEcritureComptableByRef;
    private String sqlLoadListLigneEcriture;
    private String sqlInsertEcritureComptable;
    private String sqlInsertListLigneEcritureComptable;
    private String sqlUpdateEcritureComptable;
    private String sqlDeleteEcritureComptable;
    private String sqlDeleteListLigneEcritureComptable;

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
    }

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    // ==================== Méthodes ====================
    /** sqlGetListCompteComptable */
    public void setSqlGetListCompteComptable(String pSqlGetListCompteComptable) {
        sqlGetListCompteComptable = pSqlGetListCompteComptable;
    }

    public void setSqlUpdateSequenceEcritureComptable(String pSqlUpdateSequenceEcritureComptable) {
        sqlUpdateSequenceEcritureComptable = pSqlUpdateSequenceEcritureComptable;
    }
    public void setSqlInsertSequenceEcritureComptable(String pSqlInsertSequenceEcritureComptable) {
        sqlInsertSequenceEcritureComptable = pSqlInsertSequenceEcritureComptable;
    }
    public void setSqlSelectSequenceEcritureComptable(String pSqlSelectSequenceEcritureComptable) {
        sqlSelectSequenceEcritureComptable = pSqlSelectSequenceEcritureComptable;
    }

    @Override
    public Integer getDerniereValeurSequenceEcriture(String journalCode, Integer annee) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue(JOURNAL_CODE, journalCode);
        sqlParameterSource.addValue(ANNEE, annee);
        SequenceEcritureComptableRM rowMapper = new SequenceEcritureComptableRM();
        List<SequenceEcritureComptable> sequenceEcritureComptableList = jdbcTemplate.query(sqlSelectSequenceEcritureComptable, sqlParameterSource, rowMapper);
        if (sequenceEcritureComptableList.isEmpty()) {
            return null;
        } else {
            return sequenceEcritureComptableList.get(0).getDerniereValeur();
        }
    }

    @Override
    public void insertSequenceEcriture(SequenceEcritureComptable sequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(JOURNAL_CODE, sequenceEcritureComptable.getJournalCode());
        vSqlParams.addValue(ANNEE, sequenceEcritureComptable.getAnnee());
        vSqlParams.addValue(DERNIERE_VALEUR, sequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(sqlInsertSequenceEcritureComptable, vSqlParams);

    }

    @Override
    public void updateSequenceEcriture(SequenceEcritureComptable sequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(JOURNAL_CODE, sequenceEcritureComptable.getJournalCode());
        vSqlParams.addValue(ANNEE, sequenceEcritureComptable.getAnnee());
        vSqlParams.addValue(DERNIERE_VALEUR, sequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(sqlUpdateSequenceEcritureComptable, vSqlParams);

    }

    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        return vJdbcTemplate.query(sqlGetListCompteComptable, vRM);
    }


    /** sqlGetListJournalComptable */
    public void setSqlGetListJournalComptable(String pSqlGetListJournalComptable) {
        sqlGetListJournalComptable = pSqlGetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        return vJdbcTemplate.query(sqlGetListJournalComptable, vRM);
    }

    // ==================== EcritureComptable - GET ====================

    /** sqlGetListEcritureComptable */
    public void setSqlGetListEcritureComptable(String pSqlGetListEcritureComptable) {
        sqlGetListEcritureComptable = pSqlGetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        return vJdbcTemplate.query(sqlGetListEcritureComptable, vRM);
    }


    /** sqlGetEcritureComptable */
    public void setSqlGetEcritureComptable(String pSqlGetEcritureComptable) {
        sqlGetEcritureComptable = pSqlGetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ID, pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }


    /** sqlGetEcritureComptableByRef */
    public void setSqlGetEcritureComptableByRef(String pSqlGetEcritureComptableByRef) {
        sqlGetEcritureComptableByRef = pSqlGetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(REFERENCE, pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** sqlLoadListLigneEcriture */
    public void setSqlLoadListLigneEcriture(String pSqlLoadListLigneEcriture) {
        sqlLoadListLigneEcriture = pSqlLoadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(sqlLoadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }


    // ==================== EcritureComptable - INSERT ====================

    /** sqlInsertEcritureComptable */
    public void setSqlInsertEcritureComptable(String pSqlInsertEcritureComptable) {
        sqlInsertEcritureComptable = pSqlInsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(JOURNAL_CODE, pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(REFERENCE, pEcritureComptable.getReference());
        vSqlParams.addValue(DATE, pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(LIBELLE, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(sqlInsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** sqlInsertListLigneEcritureComptable */
    public void setSqlInsertListLigneEcritureComptable(String pSqlInsertListLigneEcritureComptable) {
        sqlInsertListLigneEcritureComptable = pSqlInsertListLigneEcritureComptable;
    }
    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue(LIGNE_ID, vLigneId);
            vSqlParams.addValue(COMPTE_COMPTABLE_NUMERO, vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue(LIBELLE, vLigne.getLibelle());
            vSqlParams.addValue(DEBIT, vLigne.getDebit());

            vSqlParams.addValue(CREDIT, vLigne.getCredit());

            vJdbcTemplate.update(sqlInsertListLigneEcritureComptable, vSqlParams);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /** sqlUpdateEcritureComptable */
    public void setSqlUpdateEcritureComptable(String pSqlUpdateEcritureComptable) {
        sqlUpdateEcritureComptable = pSqlUpdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ID, pEcritureComptable.getId());
        vSqlParams.addValue(JOURNAL_CODE, pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(REFERENCE, pEcritureComptable.getReference());
        vSqlParams.addValue(DATE, pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(LIBELLE, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(sqlUpdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** sqlDeleteEcritureComptable */
    public void setSqlDeleteEcritureComptable(String pSqlDeleteEcritureComptable) {
        sqlDeleteEcritureComptable = pSqlDeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ID, pId);
        vJdbcTemplate.update(sqlDeleteEcritureComptable, vSqlParams);
    }

    /** sqlDeleteListLigneEcritureComptable */
    public void setSqlDeleteListLigneEcritureComptable(String pSqlDeleteListLigneEcritureComptable) {
        sqlDeleteListLigneEcritureComptable = pSqlDeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureId);
        vJdbcTemplate.update(sqlDeleteListLigneEcritureComptable, vSqlParams);
    }
}
