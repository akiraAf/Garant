package com.app.garant.presenter.adapters.installment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.garant.presenter.screens.installment.InstallmentRecyclerView
import com.app.garant.presenter.screens.installment.OrderRecyclerView
import com.app.garant.models.InstallmentHistoryData


class InstallmentPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        var dataInstallment =  ArrayList<InstallmentHistoryData>()
        var dataOrder =  ArrayList<InstallmentHistoryData>()

        for (i in 1..8){
            when(i) {
               1-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Одобрено",
                        "Февраль 17, 2022"
                    )
                )
                2->dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Ожидает предоплату",
                        "Февраль 17, 2022"
                    )
                )
               3-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Ожидает доставки",
                        "Февраль 17, 2022"
                    )
                )
               4-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Активный",
                        "Февраль 17, 2022"
                    )
                )
               5-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Просрочка",
                        "Февраль 17, 2022"
                    )
                )
               6-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Закрытый",
                        "Февраль 17, 2022"
                    )
                )
               7-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Отменено",
                        "Февраль 17, 2022"
                    )
                )
               else-> dataInstallment.add(
                    InstallmentHistoryData(
                        "Рассрочка #${i}",
                        "Не одобрено",
                        "Февраль 17, 2022"
                    )
                )
            }
        }

        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${1}",
                "Доставлено",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${2}",
                "Ожидает доставки",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${3}",
                "Ожидает доставки",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${4}",
                "Доставлено",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${5}",
                "Отменено",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${6}",
                "Отменено",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${7}",
                "Отменено",
                "Февраль 17, 2022"
            )
        )
        dataOrder.add(
            InstallmentHistoryData(
                "Заказ #${8}",
                "Отменено",
                "Февраль 17, 2022"
            )
        )


        return when(position){
            0->{
                val frag = InstallmentRecyclerView(dataInstallment);
                frag
            }
            else->{
                val frag = OrderRecyclerView(dataOrder);
                frag
            }
        }

    }

}