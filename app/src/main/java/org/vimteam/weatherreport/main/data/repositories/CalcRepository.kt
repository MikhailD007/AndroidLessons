package org.vimteam.weatherreport.main.data.repositories

import org.vimteam.weatherreport.main.data.mappers.CalcDataMapper
import org.vimteam.weatherreport.main.data.models.CalcDataDB
import org.vimteam.weatherreport.main.domain.contracts.CalcRepositoryContract
import org.vimteam.weatherreport.main.domain.models.CalcActions

class CalcRepository(
    private val cd: CalcDataDB
) : CalcRepositoryContract {

    override fun getCalcData() = CalcDataMapper.map(cd)

    override fun setFirstArgument(value: String) {
        cd.firstArgument = value
    }

    override fun setSecondArgument(value: String) {
        cd.secondArgument = value
    }

    override fun setAction(value: CalcActions?) {
        cd.action = value?.ordinal
    }

    override fun clear() {
        cd.firstArgument = ""
        cd.secondArgument = ""
        cd.action = null
    }


}