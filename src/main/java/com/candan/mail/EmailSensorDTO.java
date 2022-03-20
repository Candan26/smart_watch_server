package com.candan.mail;

import com.candan.mongo.swb.Max3003;
import com.candan.mongo.swb.Max30102;
import com.candan.mongo.swb.Si7021;
import com.candan.mongo.swb.SkinResistance;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class EmailSensorDTO {

    private final List<Max3003> dataMax3003;
    private final List<Max30102> dataMax300102;
    private final List<SkinResistance> dataSR;
    private final List<Si7021> dataSi7021;
}
