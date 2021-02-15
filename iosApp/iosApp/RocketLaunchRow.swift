//
//  RocketLaunchRow.swift
//  iosApp
//
//  Created by Yusuf Abdullaev on 15/02/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RocketLaunchRow: View {
    var rocketLaunch: RocketLaunch
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 10.0, content: {
                Text("Launch name: \(rocketLaunch.missionName)")
                Text(launchText).foregroundColor(launchColor)
                Text("Launch year: \(String(rocketLaunch.launchYear))")
                Text("Launch details: \(rocketLaunch.details ?? "")")
            })
            Spacer()
        }
    }
}

extension RocketLaunchRow {
    private var launchText: String {
        if let isSuccess = rocketLaunch.launchSuccess {
            return isSuccess.boolValue ? "Successful" : "Unsuccessful"
        }
        return "No Data"
    }
    private var launchColor: Color {
        if let isSuccess = rocketLaunch.launchSuccess {
            return isSuccess.boolValue ? Color.green : Color.red
        }
        return Color.gray
    }
}

struct RocketLaunchRow_Previews: PreviewProvider {
    static var previews: some View {
        RocketLaunchRow(rocketLaunch: RocketLaunch(flightNumber: 12, missionName: "Name", launchYear: 2021, launchDateUTC: "", rocket: Rocket_(id: "12", name: "FalconX", type: "Falcon"), details: "Details", launchSuccess: true, links: Links(missionPatchUrl: "", articleLink: "")))
    }
}
