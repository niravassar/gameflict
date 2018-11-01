package org.gssa.gameflict

import grails.gorm.transactions.Transactional
import org.apache.poi.ss.usermodel.Sheet
import org.grails.plugins.excelimport.AbstractExcelImporter
import org.grails.plugins.excelimport.ExcelImportService

@Transactional
class GameXlsImportService extends AbstractExcelImporter {

    ExcelImportService excelImportService

    static Map EXCEL_COLUMN_MAP = [
            sheet    : '',
            startRow : 2,
            columnMap: [
                    'A': 'gameNumber',
                    'B': 'date',
                    'C': 'time',
                    'D': 'ageGroup',
                    'E': 'field'
            ]
    ]

    protected List<Map<String, String>> parseXls(String fileName) {
        this.read(fileName)
        Sheet firstSheet = workbook.getSheetAt(0)
        EXCEL_COLUMN_MAP.sheet = firstSheet.getSheetName()
        List<Map<String, String>> gameXlsValues = excelImportService.convertColumnMapConfigManyRows(workbook, EXCEL_COLUMN_MAP)
        gameXlsValues
    }
}
